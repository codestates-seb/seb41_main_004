package com.codestates.azitserver.domain.club.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.member.dto.MemberDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ClubMemberDto {
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Signup {
		@Positive
		private Long memberId;

		@NotNull
		@Length(max = 128)
		private String joinAnswer;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Patch {
		@NotNull
		private ClubMember.ClubMemberStatus status;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Long clubMemberId;
		private MemberDto.Response member;
		private String joinAnswer;
		private ClubMember.ClubMemberStatus clubMemberStatus;
	}

}
