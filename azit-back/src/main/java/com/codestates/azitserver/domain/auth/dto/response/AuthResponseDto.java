package com.codestates.azitserver.domain.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

public class AuthResponseDto {
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
		private String profileImageName;
	}

	/**
	 * 소셜 첫 로그인 응답(회원가입 추가정보 페이지로 이동)
	 */
	@Getter
	@Setter
	public static class ResponseSocialFirst {
		private String email;
		private String nickname;
		private String password;
		private String passwordCheck;
	}

	@Getter
	@Setter
	public static class TokenResponse {
		private String accessToken;
		private String refreshToken;
	}
}