package com.codestates.azitserver.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

public class AuthDto {

	@Getter
	@Setter
	public static class Post {
		private String email;
		private String password;
	}

	@Getter
	@Setter
	public static class MatchPassword {
		private String password;
	}

	@Getter
	@Setter
	public static class PatchPassword {
		private String newPassword;
		private String newPasswordCheck;
	}

	// @Getter
	// @Setter
	// public static class sendEmail {
	// 	private String email;
	// }
	//
	// @Getter
	// @Setter
	// public static class sendPassword {
	// 	private String email;
	// 	private String authNumber;
	// }

	@Getter
	@Setter
	public static class ResponseWithProfile {
		private Long memberId;
		private String email;
		private String nickname;
		private String profileUrl;
	}
}