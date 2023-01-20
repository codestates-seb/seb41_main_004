package com.codestates.azitserver.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.entity.MemberReport;

public interface MemberReportRepository extends JpaRepository<MemberReport, Long> {
}
