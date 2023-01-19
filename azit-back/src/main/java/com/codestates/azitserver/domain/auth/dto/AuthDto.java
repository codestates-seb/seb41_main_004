package com.codestates.azitserver.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

public class AuthDto {
	/**
	 * 로그인
	 */
	@Getter
	@Setter
	public static class Post {
		private String email;
		private String password;
	}

	/**
	 * 비밀번호 인증
	 */
	@Getter
	@Setter
	public static class MatchPassword {
		private String password;
	}

	/**
	 * 비밀번호 변경
	 */
	@Getter
	@Setter
	public static class PatchPassword {
		private String newPassword;
		private String newPasswordCheck;
	}

	/**
	 * 인증번호 이메일 발송
	 */
	@Getter
	@Setter
	public static class SendEmail {
		private String email;
	}

	/**
	 * 임시 비번 이메일 발송
	 */
	@Getter
	@Setter
	public static class SendPWEmail {
		private String email;
		private String authNum;
	}
}