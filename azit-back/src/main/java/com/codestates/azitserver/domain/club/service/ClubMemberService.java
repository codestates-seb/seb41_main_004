package com.codestates.azitserver.domain.club.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.club.repository.ClubMemberRepository;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.service.MemberService;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubMemberService {
	private final ClubMemberRepository clubMemberRepository;
	private final ClubService clubService;

	private final MemberService memberService;

	public ClubMember signup(Member member, Long clubId, String joinAnswer) {
		// 참여하려는 클럽이 존재하는 클럽이거나, 취소된 클럽인지 확인힙니다.
		Club club = clubService.findClubById(clubId);
		clubService.verifyClubCanceled(club);

		// 참여하려는 호스트이거나 회원이 이미 참여 신청을 했었는지 확인합니다.
		if (verifyMemberIfClubHost(club, member.getMemberId())) {
			throw new BusinessLogicException(ExceptionCode.HOST_FAILED);
		}
		existsClubMemberByMemberIdAndClubId(member.getMemberId(), clubId);

		ClubMember clubMember = new ClubMember(member, club, joinAnswer);
		clubMemberRepository.save(clubMember);

		return club.addClubMember(clubMember);
	}

	/**
	 * 특정 아지트에 참여신청을 보낸 사용자를 전체 조회합니다.
	 * @param clubId 조회할 아지트 고유 식별자
	 * @return 해당 아지트에 참여 신청을 보낸 사용자를 담은 pagenation 배열을 반환합니다.
	 * @author cryoon
	 */
	public List<ClubMember> getAllClubMemberByClubId(Long clubId) {
		Club club = clubService.findClubById(clubId);
		return clubMemberRepository.findClubMembersByClub(club);
	}

	/**
	 * 신청한 memberId의 상태를 바꿉니다.
	 * @param member 현재 로그인한 사용자
	 * @param clubId 신청한 회원이 존재하는 아지트의 고유 식별자
	 * @param memberId 상태를 변경할 회원의 고유 식별자
	 * @param status 변경할 상태값
	 * @author cryoon
	 */
	public void updateMemberStatus(Member member, Long clubId, Long memberId, ClubMember.ClubMemberStatus status) {
		// 참여하려는 클럽이 존재하는 클럽이거나, 취소된 클럽인지 확인힙니다.
		Club club = clubService.findClubById(clubId);
		clubService.verifyClubCanceled(club);

		// 요청을 보낸 사용자가 호스트가 맞는지 확인합니다.
		if (!verifyMemberIfClubHost(club, member.getMemberId())) {
			throw new BusinessLogicException(ExceptionCode.HOST_FORBIDDEN);
		}

		// 상태를 변경하려는 사용자가 아지트에 신청한 회원이 맞고, 상태가 신청 대기가 맞는지 확인합니다.
		ClubMember clubMember = findClubMemberByMemberIdAndClubId(memberId, clubId);
		if (clubMember.getClubMemberStatus() != ClubMember.ClubMemberStatus.CLUB_WAITING) {
			throw new BusinessLogicException(ExceptionCode.INVALID_CLUB_MEMBER_STATUS);
		}

		clubMember.setClubMemberStatus(status);
		clubMemberRepository.save(clubMember);
	}

	/**
	 * 아지트에 참여한 memberId의 상태를 강퇴 상태로 변경합니다.
	 * @param member 현재 로그인한 사용자
	 * @param clubId 신청한 회원이 존재하는 아지트의 고유 식별자
	 * @param memberId 상태를 변경할 회원의 고유 식별자
	 * @author cryoon
	 */
	public void kickMember(Member member, Long clubId, Long memberId) {
		// 참여하려는 클럽이 존재하는 클럽이거나, 취소된 클럽인지 확인힙니다.
		Club club = clubService.findClubById(clubId);
		clubService.verifyClubCanceled(club);

		// 요청을 보낸 사용자가 호스트가 맞는지 확인합니다.
		if (!verifyMemberIfClubHost(club, member.getMemberId())) {
			throw new BusinessLogicException(ExceptionCode.HOST_FORBIDDEN);
		}

		// 상태를 변경하려는 사용자가 아지트에 신청한 회원이 맞고, 상태가 신청 대기가 맞는지 확인합니다.
		ClubMember clubMember = findClubMemberByMemberIdAndClubId(memberId, clubId);
		if (clubMember.getClubMemberStatus() != ClubMember.ClubMemberStatus.CLUB_JOINED) {
			throw new BusinessLogicException(ExceptionCode.INVALID_CLUB_MEMBER_STATUS);
		}

		clubMember.setClubMemberStatus(ClubMember.ClubMemberStatus.CLUB_KICKED);
		clubMemberRepository.save(clubMember);
	}

	/**
	 * 회원은 본인이 신청한 아지트를 신청 취소 할 수 있다.
	 * @param member 요청을 보낸 회원
	 * @param clubId 취소할 아지트 고유 식별자
	 * @param memberId 취소할 아지트의 회원 고유 식볋자
	 */
	public void deleteClubMember(Member member, Long clubId, Long memberId) {
		verifyMember(member, memberId);

		// 참여하려는 클럽이 존재하는 클럽이거나, 취소된 클럽인지 확인힙니다.
		clubService.findClubById(clubId);

		// 취소하려는 사용자가 아지트에 신청한 회원이 맞고, 상태가 신청 대기, 승인됨이 맞는지 확인합니다.
		ClubMember clubMember = findClubMemberByMemberIdAndClubId(memberId, clubId);
		if (clubMember.getClubMemberStatus() != ClubMember.ClubMemberStatus.CLUB_JOINED
			& clubMember.getClubMemberStatus() != ClubMember.ClubMemberStatus.CLUB_WAITING) {
			throw new BusinessLogicException(ExceptionCode.INVALID_CLUB_MEMBER_STATUS);
		}

		clubMemberRepository.deleteById(clubMember.getClubMemberId());
	}

	// *** verification ***
	public void verifyMember(Member member, Long memberId) {
		if (!member.getMemberId().equals(memberId)) {
			throw new BusinessLogicException(ExceptionCode.MEMBER_VERIFICATION_FAILED);
		}
	}

	public void existsClubMemberByMemberIdAndClubId(Long memberId, Long clubId) {
		Optional<ClubMember> optionalClubMember = clubMemberRepository.findMemberJoinClub(memberId, clubId);
		if (optionalClubMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.CLUB_MEMBER_EXISTS);
		}
	}

	public ClubMember findClubMemberByMemberIdAndClubId(Long memberId, Long clubId) {
		Optional<ClubMember> optionalClubMember = clubMemberRepository.findMemberJoinClub(memberId, clubId);
		return optionalClubMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CLUB_MEMBER_NOT_FOUND));
	}

	public boolean verifyMemberIfClubHost(Club club, Long memberId) {
		return club.getHost().getMemberId().equals(memberId);
	}

	public List<ClubMember> getAllClubMemberByMemberId(Long memberId) {
		Member member = memberService.getMemberById(memberId);
		return clubMemberRepository.findClubMembersByMember(member);
	}
}
