package com.codestates.azitserver.domain.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.entity.MemberReport;

public interface MemberReportRepository extends JpaRepository<MemberReport, Long> {

	// @Query("select mr from MemberReport mr where mr.ReporterId =: reporterId and mr.ReporteeId =: reporteeId order by created_at DESC limit 1")
	// Optional<MemberReport> findLatestReportByReporterIdAndReporteeId(Long reporterId, Long reporteeId);
}
