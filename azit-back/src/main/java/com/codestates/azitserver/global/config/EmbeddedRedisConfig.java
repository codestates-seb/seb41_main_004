package com.codestates.azitserver.global.config;

import java.io.IOException;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

@Slf4j
@Profile("test")
@Configuration
public class EmbeddedRedisConfig {
	@Value("${embeddedRedis.port}")
	private int port;

	private RedisServer redisServer;

	@PostConstruct
	public void redisServer() throws IOException {
		// System.out.println("내장 REDIS 진행 중");
		redisServer = RedisServer.builder()
			.port(port)
			.setting("maxmemory 128M")
			.build();
		redisServer.start();
	}

	@PreDestroy // 빈 제거하기 전에 마지막으로 해야하는 작업에 사용하는 어노테이션
	public void stopRedis() throws Exception {
		Optional.ofNullable(redisServer).ifPresent(RedisServer::stop);
	}
}