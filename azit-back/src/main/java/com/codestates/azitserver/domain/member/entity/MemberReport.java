package com.codestates.azitserver.domain.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.codestates.azitserver.domain.common.Auditable;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "MEMBER_REPORT")
@Setter
@Getter
@NoArgsConstructor
public class MemberReport extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REPORT_ID")
	private Long reportId;

	private Long reporterId;

	private Long reporteeId;

	@Column(name = "REPORT_CATEGORY")
	@Enumerated(value = EnumType.STRING)
	private ReportCategory reportCategory;

	@Column(name = "REPORT_REASON", length = 128)
	private String reportReason;

	@OneToMany(mappedBy = "reporter")
	@JsonManagedReference
	private List<MemberMemberReport> memberMemberReportList = new ArrayList<>();

	public enum ReportCategory {
		BAD_LANGUAGE("욕설 또는 모욕적인 언행"),
		ADVERTISE("광고, 스팸"),
		PERSONAL_INFORMATION("개인정보 노출"),
		DISCRIMINATION("차별적인 언행/행위"),
		ETC("기타");

		private String reportCategory;

		ReportCategory(String reportCategory) {
			this.reportCategory = reportCategory;
		}
	}
}
