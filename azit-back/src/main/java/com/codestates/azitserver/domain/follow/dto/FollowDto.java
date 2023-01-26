package com.codestates.azitserver.domain.follow.dto;

import com.codestates.azitserver.domain.member.dto.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FollowDto {
	@Getter
	@AllArgsConstructor
	public static class FollowStatus {
		private boolean result;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Long followId;
		private MemberDto.ClubMemberMemberResponse follower;
		private MemberDto.ClubMemberMemberResponse followee;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class GetFollowerResponse {
		private Long followId;
		private MemberDto.ClubMemberMemberResponse follower;
	}
}
