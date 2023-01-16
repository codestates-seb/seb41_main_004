package com.codestates.azitserver.domain.club.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.codestates.azitserver.domain.common.Report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ClubReport extends Report {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clubReportId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPORTED_CLUB_ID", nullable = false, updatable = false)
	private Club club;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "REPORT_CATEGORY", nullable = false)
	private ClubReport.Category reportCategory;

	public enum Category {
		ADVERTISEMENT_AND_SPAM("특정 제품, 서비스 등 광고/도배하는 아지트"),
		INAPPROPRIATE_PARTICIPATION_FEE("아지트 참가비를 개인 계좌로 입금 유도"),
		REQUEST_PERSONAL_CONTACT("개인 연락처 또는 1:1 만남 요구"),
		INVITING_OTHERS("참여인원 외 허락되지 않은 외부인원 초대"),
		PARTICULAR_RELIGION_PROPAGATION("특정 종교의 권유, 포교, 전도 목적 의심"),
		ANTISOCIAL_CONTENT("사기, 허위, 범죄, 음란물 등 오해의 소지가 있는 내용 포함"),
		ETC("기타");

		@Getter
		private final String category;

		Category(String category) {
			this.category = category;
		}
	}
}
