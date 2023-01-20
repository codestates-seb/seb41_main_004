package com.codestates.azitserver.global.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * - status는 status code + status id로 작성합니다. <br>
 * - message는 에러에 관한 구체적인 메세지를 작성합니다
 */
@Getter
@AllArgsConstructor
public enum ExceptionCode {
	// 400
	INVALID_CLUB_MEMBER_STATUS(40001, "Invalid club member status"),
	HOST_REPORT_FAILED(40002, "Host is not allowed to report its own azit"),

	// 403
	CLUB_CANCELED(40301, "The Club is canceled"),
	HOST_FORBIDDEN(40302, "Only host can access"),

	// 404
	MEMBER_NOT_FOUND(40401, "Member not found"),
	CLUB_NOT_FOUND(40402, "Club not found"),
	PASSWORD_VALIDATION_FAILED(40403, "Password confirmation failed"),
	NICKNAME_EXIST(40404, "Nickname is already in use"),
	EMAIL_EXIST(40405, "Email is already registered"),
	CLUB_MEMBER_NOT_FOUND(40406, "Join member not found"),
	TOKEN_NOT_FOUND(40407, "Token not found"),

	// 409
	MEMBER_VERIFICATION_FAILED(40901, "Member verification failed"),
	INVALID_MEETING_METHOD(40902, "Meeting method is \"offline\" or \"online\" only"),
	CLUB_MEMBER_EXISTS(40903, "Member already signed up the club before"),
	HOST_FAILED(40904, "The host cannot join its club"),
	CLUB_REPORT_EXIST(40905, "Member already report the club"),

	// 500
	INVALID_REFRESH_TOKEN(50401, "RefreshToken is invalid.");

	private final int status;
	private final String message;
}
