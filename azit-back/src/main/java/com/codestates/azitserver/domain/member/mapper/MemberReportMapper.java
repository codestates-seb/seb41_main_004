package com.codestates.azitserver.domain.member.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codestates.azitserver.domain.member.dto.MemberReportDto;
import com.codestates.azitserver.domain.member.entity.MemberReport;

@Mapper(componentModel = "spring")
public interface MemberReportMapper {

	MemberReport reportPostDtoToReport(MemberReportDto.Post reportPostDto);

	MemberReportDto.Response reportToReportResponseDto(MemberReport memberReport);

	List<MemberReportDto.Response> reportToReportResponseDto(List<MemberReport> memberReportList);
}
