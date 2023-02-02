package com.codestates.azitserver.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.azitserver.domain.member.entity.MemberReport;

public interface MemberReportRepository extends JpaRepository<MemberReport, Long> {

	// @Query("select mr from MemberReport mr where mr.ReporterId =: reporterId and mr.ReporteeId =: reporteeId order by created_at DESC limit 1")
	// Optional<MemberReport> findLatestReportByReporterIdAndReporteeId(Long reporterId, Long reporteeId);
}
