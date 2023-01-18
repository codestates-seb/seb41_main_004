package com.codestates.azitserver.global.exception;

import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.Getter;

public class BusinessLogicException extends RuntimeException {
	@Getter
	private final ExceptionCode exceptionCode;

	public BusinessLogicException(ExceptionCode exceptionCode) {
		super(exceptionCode.getMessage());
		this.exceptionCode = exceptionCode;
	}
}
