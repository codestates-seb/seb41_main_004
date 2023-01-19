package com.codestates.azitserver.domain.club.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.azitserver.domain.club.entity.ClubReport;

public interface ClubReportRepository extends JpaRepository<ClubReport, Long> {
	@Query("select cr from ClubReport cr where cr.member.memberId = :memberId and cr.club.clubId = :clubId")
	Optional<ClubReport> findReportedClubByMemberId(Long clubId, Long memberId);
}
