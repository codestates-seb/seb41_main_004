package com.codestates.azitserver.domain.stub;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.codestates.azitserver.domain.follow.dto.FollowDto;
import com.codestates.azitserver.domain.follow.entity.Follow;

public class FollowStubData {
	public static Follow getDefaultFollow() {
		Follow.Builder builder = new Follow.Builder(MemberStubData.stubMember(), MemberStubData.stubMember());
		builder.matpal(true);
		return builder.build();
	}

	public static FollowDto.Response getFollowDtoResponse() {
		FollowDto.Response response = new FollowDto.Response();
		response.setFollowId(1L);
		response.setFollower(MemberStubData.getClubMemberMemberResponse());
		response.setFollowee(MemberStubData.getClubMemberMemberResponse());

		return response;
	}

	public static FollowDto.GetFollowerResponse getFollowDtoGetFollowerResponse() {
		FollowDto.GetFollowerResponse response = new FollowDto.GetFollowerResponse();
		response.setFollowId(1L);
		response.setFollower(MemberStubData.getClubMemberMemberResponse());
		response.setMatpal(true);

		return response;
	}

	public static FollowDto.GetFollowingResponse getFollowDtoGetFollowingResponse() {
		FollowDto.GetFollowingResponse response = new FollowDto.GetFollowingResponse();
		response.setFollowId(1L);
		response.setFollowee(MemberStubData.getClubMemberMemberResponse());
		response.setMatpal(true);

		return response;
	}

	public static FollowDto.FollowStatus getFollowDtoFollowStatus() {
		return new FollowDto.FollowStatus(true);
	}

	public static Page<Follow> getDefaultPageFollow() {
		return new PageImpl<>(List.of(getDefaultFollow()), PageRequest.of(0, 10,
			Sort.by("createdAt").descending()), 1);
	}

}
