#!/bin/bash
BUILD_LOG=/home/ubuntu/action/deploy.log

BUILD_JAR=$(ls /home/ubuntu/action/build/libs/azit-server-0.0.1-SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_JAR)

echo "> 현재 시간: $(date)" >> $BUILD_LOG

echo "> build 파일명: $JAR_NAME" >> $BUILD_LOG

echo "> build 파일 복사" >> $BUILD_LOG
DEPLOY_PATH=/home/ubuntu/action/
cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> $BUILD_LOG
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $BUILD_LOG
else
  echo "> kill -9 $CURRENT_PID" >> $BUILD_LOG
  sudo kill -9 $CURRENT_PID
  sleep 5
fi

UUID=$(uuidgen)
echo "> 로그파일 생성 $UUID.log" >> $BUILD_LOG
sudo mkdir -p /home/ubuntu/action/logs && sudo touch /home/ubuntu/action/logs/$UUID.log
sudo chmod -R 777 /home/ubuntu/action/logs/$UUID.log

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 배포"    >> $BUILD_LOG
sudo nohup java -jar $DEPLOY_JAR --spring.profiles.active=server,aws >> /home/ubuntu/action/logs/$UUID.log 2>/home/ubuntu/action/deploy_err.log &