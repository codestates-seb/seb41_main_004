package com.codestates.azitserver.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

public class LoginDto {

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

	@Getter
	@Setter
	public static class Response {
		private String email;
		private String nickname;
		private String profileUrl;
	}
}
