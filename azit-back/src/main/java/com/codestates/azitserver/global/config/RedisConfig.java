package com.codestates.azitserver.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	// RedisTemplate를 통해 레디스와 통신

	@Value("${redis.host}")
	private String host;

	@Value("${redis.port}")
	private int port;

	// @Value("${redis.password}")
	// private String password;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(host, port);
	}

	// @Bean
	// public LettuceConnectionFactory redisConnectionFactory() {
	// 	// System.out.println("외장 REDIS 진행 중");
	// 	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
	// 	redisStandaloneConfiguration.setHostName(host);
	// 	redisStandaloneConfiguration.setPort(port);
	// 	redisStandaloneConfiguration.setPassword(password);
	//
	// 	return new LettuceConnectionFactory(redisStandaloneConfiguration);
	// }

	@Bean
	public RedisTemplate<String, String> redisTemplate() {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

		//key, value serializer
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory());

		return redisTemplate;
	}
}