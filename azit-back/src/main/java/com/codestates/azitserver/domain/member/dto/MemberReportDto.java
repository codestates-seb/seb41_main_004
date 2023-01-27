package com.codestates.azitserver.domain.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

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

		@NotNull(message = "Reporter Id is required")
		private Long reporterId;

		@NotNull(message = "Reportee Id is required")
		private Long reporteeId;

		private MemberReport.ReportCategory reportCategory;

		@Length(max = 128, message = "Reason for report must be no longer than 128 characters")
		private String reportReason;
	}

	@Getter
	@Setter
	public static class Response {
		private Long reportId;
		private Long reporterId;
		private Long reporteeId;
		private MemberReport.ReportCategory reportCategory;
		private String reportReason;
	}
}
