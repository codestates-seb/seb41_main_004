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
	INVALID_TOKEN(40003, "Invalid token"),
	INVALID_APPLY_FOLLOW_(40004, "Member cannot follow yourself"),

	// 403
	CLUB_CANCELED(40301, "The Club is canceled"),
	HOST_FORBIDDEN(40302, "Only host can access"),

	// 404
	MEMBER_NOT_FOUND(40401, "Member not found"),
	CLUB_NOT_FOUND(40402, "Club not found"),
	PASSWORD_VALIDATION_FAILED(40403, "Password confirmation failed"),
	NICKNAME_EXIST_SIGNUP(40404, "Nickname is already in use"),
	EMAIL_EXIST_SIGNUP(40405, "Email is already registered"),
	CLUB_MEMBER_NOT_FOUND(40406, "Join member not found"),
	TOKEN_NOT_FOUND(40407, "Token not found"),
	STRING_VALIDATION_FAILED(40408, "String confirmation failed"),
	AUTH_NUMBER_NOT_FOUND(40409, "String confirmation failed"),
	REVIEW_NOT_FOUND(40410, "Review not found"),

	REPORT_NOT_FOUND(40411, "Member Report not found"),

	NICKNAME_EXIST_CHECK_ONLY(40411, "Just checking: Nickname is already in use"),
	EMAIL_EXIST_CHECK_ONLY(40412, "Just checking: Email is already registered"),
	FOLLOW_NOT_FOUND(40413, "Follow not found"),

	CATEGORY_SMALL_NOT_FOUND(40414, "Category(small) not found"),

	NO_TARGET_TO_CHECK(40415, "Just checking: Type at least one object(Nickname or Email)"),

	// 406
	CLUB_MEMBER_FULL(40601, "Club member sold out"),

	// 409
	MEMBER_VERIFICATION_FAILED(40901, "Member verification failed"),
	INVALID_MEETING_METHOD(40902, "Meeting method is \"offline\" or \"online\" only"),
	CLUB_MEMBER_EXISTS(40903, "Member already signed up the club before"),
	HOST_FAILED(40904, "The host cannot join its club"),
	CLUB_REPORT_EXIST(40905, "Member already report the club"),
	RESTRICTED_UPDATE_FIELDS(40906, "Cannot update the azit because participants exist"),
	REVIEW_ALREADY_EXIST(40906, "Review already exist"),
	FOLLOW_EXIST(40907, "Follow already exist"),


	// 500
	INVALID_REFRESH_TOKEN(50401, "RefreshToken is invalid.");

	private final int status;
	private final String message;
}
