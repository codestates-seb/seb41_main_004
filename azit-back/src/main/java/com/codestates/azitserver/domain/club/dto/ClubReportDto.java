package com.codestates.azitserver.domain.club.dto;

import com.codestates.azitserver.domain.club.entity.ClubReport;
import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.global.validator.EnumValue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ClubReportDto {
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post {
		@EnumValue(enumClass = ClubReport.Category.class, ignoreCase = true)
		private String reportCategory;
		private String reportReason;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Long clubReportId;
		private ClubDto.ReportClubResponse club;
		private MemberDto.ResponseEmailAndNickname member;
		private ClubReport.Category reportCategory;
		private String reportReason;
	}
}
