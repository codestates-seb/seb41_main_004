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


	// 404
	MEMBER_NOT_FOUND(40401, "Member not found"),
	CLUB_NOT_FOUND(40402, "Club not found"),
	PASSWORD_VALIDATION_FAILED(40403, "비밀번호가 서로 다릅니다"),
	NICKNAME_EXIST(40404,"존재하는 닉네임입니다"),
	EMAIL_EXIST(40405,"이미 가입된 이메일입니다");

	// 500

	private int status;
	private String message;


}
