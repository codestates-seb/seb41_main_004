package com.codestates.azitserver.domain.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

public class AuthResponseDto {
	/**
	 * 인증번호 발송 응답
	 */
	@Getter
	@Setter
	public static class ResponseEmail {
		private String email;
		private String serverNumber; // 서버가 발급해준 인증번호
	}

	/**
	 * 로그인 응답
	 */
	@Getter
	@Setter
	public static class ResponseWithProfile {
		private Long memberId;
		private String email;
		private String nickname;
		private String profileUrl;
	}
}