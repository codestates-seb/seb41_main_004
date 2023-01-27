package com.codestates.azitserver.domain.club.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.codestates.azitserver.domain.club.dto.ClubMemberDto;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.member.mapper.MemberMapper;

@Mapper(componentModel = "spring", uses = {MemberMapper.class})
public interface ClubMemberMapper {
	ClubMemberDto.Response clubMemberToClubMemberDtoResponse(ClubMember clubMember);

	List<ClubMemberDto.Response> clubMemberToClubMemberDtoResponse(List<ClubMember> clubMembers);

	ClubMemberDto.ClubMemberStatusResponse clubMemberToClubMemberDtoClubMemberStatusResponse(ClubMember clubMember);

	List<ClubMemberDto.ClubMemberStatusResponse> clubMemberToClubMemberDtoClubMemberStatusResponse(List<ClubMember> clubMemberList);
}
