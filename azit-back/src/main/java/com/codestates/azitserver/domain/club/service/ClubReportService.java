package com.codestates.azitserver.domain.club.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.entity.ClubReport;
import com.codestates.azitserver.domain.club.repository.ClubReportRepository;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubReportService {
	private final ClubReportRepository clubReportRepository;
	private final ClubService clubService;

	public ClubReport createClubReport(Member member, Long clubId, ClubReport clubReport) {
		// trick : 비로그인 사용자가 신고를 하면 실제 신고 정보는 db에 저장되지 않고 신고 내욤만 반환합니다.
		if (member == null) {
			Member memberTemp = new Member();
			memberTemp.setMemberId(0L);
			memberTemp.setNickname("Anonymous");
			memberTemp.setEmail("None");

			clubReport.setClubReportId(0L);
			clubReport.setMember(memberTemp);
			return clubReport;
		}

		// 호스트는 본인의 아지트를 신고할 수 없습니다.
		verifyMemberIsClubHost(member, clubId);

		// 이미 신고한 적이 있는지 확인합니다.
		verifyIfClubReportExist(clubId, member.getMemberId());

		clubReport.setMember(member);

		return clubReportRepository.save(clubReport);
	}

	private void verifyIfClubReportExist(Long clubId, Long memberId) {
		Optional<ClubReport> optionalClubReport = clubReportRepository.findReportedClubByMemberId(clubId, memberId);
		if (optionalClubReport.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.CLUB_REPORT_EXIST);
		}
	}

	private void verifyMemberIsClubHost(Member member, Long clubId) {
		Club club = clubService.findClubById(clubId);

		if (member.getMemberId().equals(club.getHost().getMemberId())) {
			throw new BusinessLogicException(ExceptionCode.HOST_REPORT_FAILED);
		}
	}
}
