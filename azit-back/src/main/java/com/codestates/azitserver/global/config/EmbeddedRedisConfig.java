package com.codestates.azitserver.global.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

@Slf4j
@Profile("test")
@Configuration
public class EmbeddedRedisConfig {
	// @Value("${embeddedRedis.port}")
	@Value("${redis.port}")
	private int port;

	private RedisServer redisServer;

	@PostConstruct
	public void redisServer() throws IOException {
		System.out.println("내장 REDIS 진행 중");
		redisServer = RedisServer.builder()
			.port(port)
			.setting("maxmemory 128M")
			.build();

		try {
			redisServer.start();
		} catch (Exception e) {
			log.error("이미 실행중인 포트", e);
		}

		// System.out.println("내장 REDIS 진행 중");
	}

	@PreDestroy // 빈 제거하기 전에 마지막으로 해야하는 작업에 사용하는 어노테이션
	public void stopRedis() throws Exception {
		Optional.ofNullable(redisServer).ifPresent(RedisServer::stop);
	}

	// /**
	//  * Embedded Redis가 현재 실행중인지 확인
	//  */
	// private boolean isRedisRunning() throws IOException {
	// 	return isRunning(executeGrepProcessCommand(port));
	// }
	//
	// /**
	//  * 현재 PC/서버에서 사용가능한 포트 조회
	//  */
	// public int findAvailablePort() throws IOException {
	//
	// 	for (int port = 10000; port <= 65535; port++) {
	// 		Process process = executeGrepProcessCommand(port);
	// 		if (!isRunning(process)) {
	// 			return port;
	// 		}
	// 	}
	//
	// 	throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
	// }
	//
	// /**
	//  * 해당 port를 사용중인 프로세스 확인하는 sh 실행
	//  */
	// private Process executeGrepProcessCommand(int port) throws IOException {
	// 	String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
	// 	String[] shell = {"/bin/sh", "-c", command};
	// 	return Runtime.getRuntime().exec(shell);
	// }
	//
	// /**
	//  * 해당 Process가 현재 실행중인지 확인
	//  */
	// private boolean isRunning(Process process) {
	// 	String line;
	// 	StringBuilder pidInfo = new StringBuilder();
	//
	// 	try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
	//
	// 		while ((line = input.readLine()) != null) {
	// 			pidInfo.append(line);
	// 		}
	//
	// 	} catch (Exception e) {
	// 	}
	//
	// 	return StringUtils.hasText(pidInfo.toString());
	// }
}