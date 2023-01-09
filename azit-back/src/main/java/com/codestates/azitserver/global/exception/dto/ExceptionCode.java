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
	CLUB_NOT_FOUND(40402, "Club not found");

	// 500

	final int status;
	final String message;
}
