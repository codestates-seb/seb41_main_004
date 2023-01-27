package com.codestates.azitserver.domain.common;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.codestates.azitserver.domain.member.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Inheritance 사용안한이유: https://ict-nroo.tistory.com/128

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Report extends Auditable{
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPORTER_ID", nullable = false, updatable = false)
	private Member member;

	@Column(nullable = true, length = 128)
	private String reportReason;
}
