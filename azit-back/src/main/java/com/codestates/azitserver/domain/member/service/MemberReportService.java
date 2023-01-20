package com.codestates.azitserver.domain.member.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.member.entity.MemberReport;
import com.codestates.azitserver.domain.member.repository.MemberReportRepository;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReportService {

	public MemberReport createMemberReport(MemberReport tempMemberReport) {
		reportOnlyOnceADay(tempMemberReport);

		return memberReportRepository.save(tempMemberReport);
	}
	private final MemberReportRepository memberReportRepository;

	public List<MemberReport> getAllMemberReport() { return memberReportRepository.findAll();}

	public void deleteMemberReportById(Long memberReportId) {
		memberReportRepository.deleteById(memberReportId);
	}

	public MemberReport getLatestMemberReportBy2Ids(Long reporterId, Long reporteeId) {
			// return memberReportRepository.findLatestReportByReporterIdAndReporteeId(reporterId, reporterId)
			// 	.orElseThrow(() -> new BusinessLogicException(ExceptionCode.REPORT_NOT_FOUND));
		return null;
	}



	public MemberReport reportOnlyOnceADay(MemberReport memberReport) {
		// 현재시간을 yyyy/mm/dd 포맷의 String으로 만들기
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String formattedNow = now.format(formatter);

		// memberReport 의 신고자, 피신고자 정보로 repository 에 해당 신고내역 있는지 검색

	return memberReport;
	}
}
