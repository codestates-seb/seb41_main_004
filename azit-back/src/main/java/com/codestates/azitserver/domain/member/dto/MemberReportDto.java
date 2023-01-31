package com.codestates.azitserver.domain.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.entity.MemberReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberReportDto {

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Post {

		private MemberReport.ReportCategory reportCategory;

		@Length(max = 128, message = "Reason for report must be no longer than 128 characters")
		private String reportReason;
	}

	@Getter
	@Setter
	public static class Response {
		private Long reportId;
		private MemberDto.Response reporter;
		private MemberDto.Response reportee;
		private MemberReport.ReportCategory reportCategory;
		private String reportReason;
	}
}
