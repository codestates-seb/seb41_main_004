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
	// 403
	CLUB_CANCELED(40301, "The Club is canceled"),

	// 404
	MEMBER_NOT_FOUND(40401, "Member not found"),
	CLUB_NOT_FOUND(40402, "Club not found"),
	PASSWORD_VALIDATION_FAILED(40403, "Password confirmation failed"),
	NICKNAME_EXIST(40404,"Nickname is already in use"),
	EMAIL_EXIST(40405,"Email is already registered"),

	// 409
	MEMBER_VERIFICATION_FAILED(40901, "Member verification failed"),
	INVALID_MEETING_METHOD(40902, "Meeting method is \"offline\" or \"online\" only"),
	ALREADY_JOINED(40903, "Member already signed up the club before"),
	HOST_FAILED(40904, "The host cannot join its club" );

	// 500

	private int status;
	private String message;


}
