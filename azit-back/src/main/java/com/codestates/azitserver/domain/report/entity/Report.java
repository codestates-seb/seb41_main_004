package com.codestates.azitserver.domain.report.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.codestates.azitserver.domain.common.Auditable;

public class Report extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REPORT_ID")
	private Long reportId;

	@Column(name = "REPORT_CATEGORY")
	private ReportCategory reportCategory;

	public enum ReportCategory {
		CATE1("asdf"),
		CATE2("asdfg");

		private String reportCategory;

		ReportCategory(String reportCategory) {
			this.reportCategory = reportCategory;
		}
	}
}
