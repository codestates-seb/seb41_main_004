package com.codestates.azitserver.domain.auth.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisUtils {
	private final RedisTemplate<String, String> redisTemplate;

	public void setData(String key, String email, Long expiration) {
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(email.getClass()));
		redisTemplate.opsForValue().set(key, email, expiration, TimeUnit.MINUTES);
	}
}
