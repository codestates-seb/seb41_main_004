package com.codestates.azitserver.domain.member.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "MEMBER_MEMBER_REPORT")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberMemberReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberMemberReportId; // PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPORTER_MEMBER_ID")
	@JsonBackReference
	private Member reporter; //FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPORTEE_MEMBER_ID")
	@JsonBackReference
	private Member reportee; //FK

}
