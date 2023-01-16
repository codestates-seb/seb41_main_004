package com.codestates.azitserver.domain.club.service;

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
		Club club = clubService.findClubById(clubId);
		ClubMember clubMember = new ClubMember(member, club, joinAnswer);
		clubMemberRepository.save(clubMember);

		return club.addClubMember(clubMember);
	}

	public void verifyMember(Member member, Long memberId) {
		if (!member.getMemberId().equals(memberId)) {
			throw new BusinessLogicException(ExceptionCode.MEMBER_VERIFICATION_FAILED);
		}
	}
}
