package com.codestates.azitserver.domain.club.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.club.repository.ClubMemberRepository;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubMemberService {
	private final ClubMemberRepository clubMemberRepository;
	private final ClubService clubService;

	public ClubMember signup(Member member, Long clubId, String joinAnswer) {
		// 참여하려는 클럽이 존재하는 클럽이거나, 취소된 클럽인지 확인힙니다.
		Club club = clubService.findClubById(clubId);
		clubService.verifyClubCanceled(club);

		// 참여하려는 호스트이거나 회원이 이미 참여 신청을 했었는지 확인합니다.
		verifyClubHost(club, member.getMemberId());
		findMemberJoinClub(member.getMemberId(), clubId);

		ClubMember clubMember = new ClubMember(member, club, joinAnswer);
		clubMemberRepository.save(clubMember);

		return club.addClubMember(clubMember);
	}

	// *** verification ***
	public void verifyMember(Member member, Long memberId) {
		if (!member.getMemberId().equals(memberId)) {
			throw new BusinessLogicException(ExceptionCode.MEMBER_VERIFICATION_FAILED);
		}
	}

	public void findMemberJoinClub(Long memberId, Long clubId) {
		Optional<ClubMember> optionalClubMember = clubMemberRepository.findMemberJoinClub(memberId, clubId);
		if (optionalClubMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.ALREADY_JOINED);
		}
	}

	public void verifyClubHost(Club club, Long memberId) {
		if (club.getHost().getMemberId().equals(memberId)) {
			throw new BusinessLogicException(ExceptionCode.HOST_FAILED);
		}
	}
}
