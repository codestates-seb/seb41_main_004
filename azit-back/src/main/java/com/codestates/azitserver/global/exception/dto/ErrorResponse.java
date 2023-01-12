package com.codestates.azitserver.global.exception.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private int status;
	private String message;
	private List<FieldError> fieldErrors;
	private List<ConstraintViolationError> violationErrors;

	private ErrorResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	private ErrorResponse(List<FieldError> fieldErrors, List<ConstraintViolationError> violationErrors) {
		this.fieldErrors = fieldErrors;
		this.violationErrors = violationErrors;
	}

	public static ErrorResponse of(BindingResult bindingResult) {
		return new ErrorResponse(FieldError.of(bindingResult), null);
	}

	public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {
		return new ErrorResponse(null, ConstraintViolationError.of(violations));
	}

	public static ErrorResponse of(ExceptionCode exceptionCode) {
		return new ErrorResponse(exceptionCode.getStatus() / 100, exceptionCode.getMessage());
	}

	public static ErrorResponse of(HttpStatus httpStatus) {
		return new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase());
	}

	public static ErrorResponse of(HttpStatus httpStatus, String message) {
		return new ErrorResponse(httpStatus.value(), message);
	}

	@Getter
	public static class FieldError {
		private final String field;
		private final Object rejectedValue;
		private final String message;

		private FieldError(String field, Object rejectedValue, String message) {
			this.field = field;
			this.rejectedValue = rejectedValue;
			this.message = message;
		}

		public static List<FieldError> of(BindingResult bindingResult) {
			List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
			return fieldErrors.stream()
				.map(error -> new FieldError(
					error.getField(),
					error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
					error.getDefaultMessage()))
				.collect(Collectors.toList());
		}
	}

	@Getter
	private static class ConstraintViolationError {
		private final String propertyPath;
		private final Object invalidValue;
		private final String message;

		private ConstraintViolationError(String propertyPath, Object invalidValue, String message) {
			this.propertyPath = propertyPath;
			this.invalidValue = invalidValue;
			this.message = message;
		}

		public static List<ConstraintViolationError> of(Set<ConstraintViolation<?>> constraintViolations) {
			return constraintViolations.stream()
				.map(error -> new ConstraintViolationError(
					error.getPropertyPath().toString(),
					error.getInvalidValue().toString(),
					error.getMessage()))
				.collect(Collectors.toList());
		}
	}
}
