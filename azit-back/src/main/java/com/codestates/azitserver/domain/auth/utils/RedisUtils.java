package com.codestates.azitserver.domain.auth.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisUtils {
	private final RedisTemplate<String, String> redisTemplate;

	public void setData(String key, String email, Long expiration) {
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(email.getClass()));
		redisTemplate.opsForValue().set(key, email, expiration, TimeUnit.MINUTES);
	}

	public Long getExpiration(String refreshToken) {
		return redisTemplate.getExpire(refreshToken);
	}

	public String getEmail(String refreshToken) {
		if (!StringUtils.hasText(refreshToken)) {
			throw new BusinessLogicException(ExceptionCode.INVALID_REFRESH_TOKEN);
		}
		String Email = redisTemplate.opsForValue().get(refreshToken);
		if (Email.isEmpty()) {
			throw new BusinessLogicException(ExceptionCode.INVALID_REFRESH_TOKEN);
		}
		return Email;
	}

}
