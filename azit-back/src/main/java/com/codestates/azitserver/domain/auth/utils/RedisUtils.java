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

	public void setData(String email, String token, Long expiration) {
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(email.getClass()));
		redisTemplate.opsForValue().set(email, token, expiration, TimeUnit.MINUTES);
	}

	public void deleteData(String email) {
		redisTemplate.delete(email);
	}

	public boolean isExists(String email) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(email));
	}

	public String getRTKbyEmail(String email) {
		if (!StringUtils.hasText(email)) {
			throw new BusinessLogicException(ExceptionCode.INVALID_REFRESH_TOKEN);
		}
		String refreshToken = redisTemplate.opsForValue().get(email);
		if (refreshToken.isEmpty()) {
			throw new BusinessLogicException(ExceptionCode.INVALID_REFRESH_TOKEN);
		}
		return refreshToken;
	}
}
