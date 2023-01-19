package com.codestates.azitserver.domain.club.mapper;

import org.mapstruct.Mapper;

import com.codestates.azitserver.domain.club.dto.ClubReportDto;
import com.codestates.azitserver.domain.club.entity.ClubReport;
import com.codestates.azitserver.domain.member.mapper.MemberMapper;

@Mapper(componentModel = "spring", uses = {ClubMapper.class, MemberMapper.class})
public interface ClubReportMapper {
	ClubReport clubReportDtoPostToClubReport(ClubReportDto.Post post);

	ClubReportDto.Response clubReportToClubReportDtoResponse(ClubReport clubReport);
}
