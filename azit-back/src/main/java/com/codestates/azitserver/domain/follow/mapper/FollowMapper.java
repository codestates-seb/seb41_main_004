package com.codestates.azitserver.domain.follow.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.codestates.azitserver.domain.follow.dto.FollowDto;
import com.codestates.azitserver.domain.follow.entity.Follow;
import com.codestates.azitserver.domain.member.mapper.MemberMapper;

@Mapper(componentModel = "spring", uses = MemberMapper.class)
public interface FollowMapper {
	FollowDto.Response followToFollowDtoResponse(Follow follow);

	List<FollowDto.Response> followToFollowDtoResponse(List<Follow> follow);

	List<FollowDto.GetFollowerResponse> followToFollowDtoGetFollowerResponse(List<Follow> follows);
}
