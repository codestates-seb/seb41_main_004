package com.codestates.azitserver.domain.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.codestates.azitserver.domain.fileInfo.entity.FileInfo;
import com.codestates.azitserver.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDto {

	@Getter //인증 파트 테스트를 위해 회원 생성만 유효성 검증 적용
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Post {
		@NotBlank(message = "Email is required")
		@Email(message = "Invalid email address")
		private String email;

		@NotBlank(message = "Nickname is required")
		@Length(min = 2, max = 8, message = "Nickname must be between 2 and 8 characters long")
		private String nickname;

		@NotBlank(message = "Password is required")

		@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,16}$",
			message = "Password must be between 8 and 16 characters long with one letter, one number and one special symbol")
		private String password;

		@NotBlank(message = "Reenter password")
		private String passwordCheck;

		private Member.Gender gender;

		@Pattern(regexp = "\\d{4}", message = "Years of birth must be 4 digit number")
		private String birthYear;

		@Length(max = 128, message = "Introduction must be no longer than 128 characters")
		private String aboutMe;

	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Patch {
		// 이메일 변경 불가
		//        @NotBlank(message = "이메일을 입력해 주세요")
		//        @Email(message = "유효한 이메일 형식이 아닙니다")
		//        private String email;

		private Long memberId;

		@NotBlank(message = "")
		@Length(min = 2, max = 16, message = "Nickname must be between 2 and 8 characters long")
		private String nickname;

		@NotBlank(message = "Password is required")
		@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,16}$",
			message = "Password must be between 8 and 16 characters long with one letter, one number and one special symbol")
		private String password;

		@NotBlank(message = "Reenter password")
		private String passwordCheck;

		@Length(max = 128, message = "Introduction must be no longer than 128 characters")
		private String aboutMe;

	}

	@Getter
	@Setter
	public static class Response {
		private Long memberId;
		private FileInfo fileInfo;
		private String email;
		private String nickname;
		private String password;
		private Member.Gender gender;
		private String birthYear;
		private String aboutMe;
		private Integer reputation;
		private Member.MemberStatus memberStatus;
	}
}
