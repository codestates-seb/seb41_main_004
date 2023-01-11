package com.codestates.azitserver.domain.member.mapper;

import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "memberId", ignore = true)
    @Mapping(target = "memberStatus", ignore = true)
    @Mapping(target = "aboutMe", ignore = true)
    Member memberPostDtoToMember(MemberDto.Post memberPostDto);

    @Mapping(target = "aboutMe", ignore = true)
    Member memberPatchDtoToMember(MemberDto.Patch memberPatchDto);

    MemberDto.Response memberToMemberResponseDto(Member member);

    List<MemberDto.Response> membersToMemberResponseDtos(List<Member> members);
}
