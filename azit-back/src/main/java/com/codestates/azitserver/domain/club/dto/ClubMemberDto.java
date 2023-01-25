package com.codestates.azitserver.domain.club.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.global.validator.EnumValue;

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
		@EnumValue(enumClass = ClubMember.ClubMemberStatus.class, ignoreCase = true)
		private String status;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Long clubMemberId;
		private MemberDto.ClubMemberMemberResponse member;
		private String joinAnswer;
		private ClubMember.ClubMemberStatus clubMemberStatus;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ClubMemberParticipationStatusResponse {
		private List<Long> clubMemberIdList;
		private List<Boolean> participationStatusList;
	}
}
