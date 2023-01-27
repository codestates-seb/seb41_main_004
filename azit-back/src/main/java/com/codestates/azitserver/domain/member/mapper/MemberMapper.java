package com.codestates.azitserver.domain.member.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {

	@Mapping(target = "memberId", ignore = true)
	@Mapping(target = "memberStatus", ignore = true)
	@Mapping(target = "fileInfo", ignore = true)
	@Mapping(target = "reputation", ignore = true)
	Member memberPostDtoToMember(MemberDto.Post memberPostDto);

	@Mapping(target = "fileInfo", ignore = true)
	@Mapping(target = "reputation", ignore = true)
	Member memberPatchDtoToMember(MemberDto.Patch memberPatchDto);

	@Mapping(target = "follower", expression = "java(member.getFollowerList().size())")
	@Mapping(target = "following", expression = "java(member.getFollowingList().size())")
	MemberDto.Response memberToMemberResponseDto(Member member);

	List<MemberDto.Response> membersToMemberResponseDtos(List<Member> members);
	
	MemberDto.ClubMemberMemberResponse memberToClubMemberMemberResponse(Member member);

	MemberDto.ParticipantResponse memberToParticipantResponse(Member member);
}
