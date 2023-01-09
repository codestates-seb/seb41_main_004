package com.codestates.azitserver.global.exception.advice;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		log.debug("Method argument not valid exception occurred: {}", exception.getMessage(), exception);
		final ErrorResponse response = ErrorResponse.of(exception.getBindingResult());

		return response;
	}

	@ExceptionHandler({
		IllegalStateException.class, IllegalArgumentException.class, TypeMismatchException.class,
		HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, MultipartException.class,
	})
	public ErrorResponse handleBadRequestException(Exception exception) {
		log.debug("Bad request exception occurred: {}", exception.getMessage(), exception);
		ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, exception.getMessage());

		return response;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
		log.debug("Http request method not supported exception occurred: {}", exception.getMessage(), exception);
		final ErrorResponse response = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);

		return response;
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleBusinessLogicException(BusinessLogicException exception) {
		log.debug("Business logic exception occurred: {}", exception.getMessage(), exception);
		final ErrorResponse response = ErrorResponse.of(exception.getExceptionCode());

		return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getExceptionCode().getStatus() / 100));
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {
		log.debug("Constraint violation exception occurred: {}", exception.getMessage(), exception);
		final ErrorResponse response = ErrorResponse.of(exception.getConstraintViolations());

		return response;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleException(Exception e) {
		log.error("Internal server_error occurred: ", e);  // Internal server error는 추가 예외처리가 필요합니다.
		final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);

		return response;
	}
}

