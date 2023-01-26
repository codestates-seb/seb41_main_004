package com.codestates.azitserver.domain.follow.dto;

import com.codestates.azitserver.domain.member.dto.MemberDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FollowDto {
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Long followId;
		private MemberDto.ClubMemberMemberResponse follower;
		private MemberDto.ClubMemberMemberResponse followee;
	}
}
