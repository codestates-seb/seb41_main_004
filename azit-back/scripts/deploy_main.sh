#!/bin/bash
DEPLOY_LOG=/home/ubuntu/action/deploy_production.log
BUILD_LOG=/home/ubuntu/action/logs/production
PORT=8080
UUID=$(uuidgen)
SPRING_OPTIONS=--spring.profiles.active=server,aws

BUILD_JAR=$(ls /home/ubuntu/action/build/libs/azit-server-0.0.1-SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_JAR)

echo "> 현재 시간: $(date)" >> $DEPLOY_LOG

echo "> build 파일명: $JAR_NAME" >> $DEPLOY_LOG

echo "> build 파일 복사" >> $DEPLOY_LOG
DEPLOY_PATH=/home/ubuntu/action/
cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> $DEPLOY_LOG
CURRENT_PID=$( lsof -i :$PORT | grep java | awk '{print $2}')

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $DEPLOY_LOG
else
  echo "> kill -9 $CURRENT_PID" >> $DEPLOY_LOG
  sudo kill -9 $CURRENT_PID
  sleep 5
fi

echo "> 로그파일 생성 $UUID.log" >> $DEPLOY_LOG
sudo mkdir -p $BUILD_LOG && sudo touch $BUILD_LOG/$UUID.log
sudo chmod -R 777 $BUILD_LOG/$UUID.log

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 배포"    >> $DEPLOY_LOG
sudo nohup java -jar $DEPLOY_JAR $SPRING_OPTIONS >> $BUILD_LOG/$UUID.log 2>/home/ubuntu/action/deploy_err.log &