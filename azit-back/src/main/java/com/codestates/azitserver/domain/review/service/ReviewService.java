package com.codestates.azitserver.domain.review.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.club.service.ClubMemberService;
import com.codestates.azitserver.domain.club.service.ClubService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.service.MemberService;
import com.codestates.azitserver.domain.review.entity.Review;
import com.codestates.azitserver.domain.review.repository.ReviewRepository;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final MemberService memberService;
	private final ClubService clubService;
	private final ClubMemberService clubMemberService;

	public Review createReview(Review review) {
		// 리뷰의 대상들이 존재하는지 확인
		Member reviewer = memberService.findExistingMember(review.getReviewer().getMemberId());
		Member reviewee = memberService.findExistingMember(review.getReviewee().getMemberId());

		// 참여한 클럽이 존재하는지 확인
		Club club = clubService.findClubById(review.getClub().getClubId());

		// 리뷰 대상들이 클럽에 참여한 회원들이 맞는지 확인
		verifyReviewMemberIsClubMember(club, reviewer);
		verifyReviewMemberIsClubMember(club, reviewee);

		// 이미 해당 리뷰를 작성한 적이 있는지 확인
		Optional<Review> optionalReview = reviewRepository.findReviewAlreadyWritten(reviewer, reviewee, club);
		if (optionalReview.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.REVIEW_ALREADY_EXIST);
		}

		return reviewRepository.save(review);
	}

	// *** verification ***//
	public boolean isHost(Club club, Long memberId) {
		return club.getHost().getMemberId().equals(memberId);
	}

	public void verifyReviewMemberIsClubMember(Club club, Member member) {
		// 아지트의 호스트는 리뷰 대상자입니다.
		if (isHost(club, member.getMemberId())) {
			return;
		}

		// 아지트 참여인원이지 확인합니다.
		ClubMember clubMember = clubMemberService.findClubMemberByMemberIdAndClubId(member.getMemberId(),
			club.getClubId());
		if (clubMember.getClubMemberStatus() != ClubMember.ClubMemberStatus.CLUB_JOINED) {
			throw new BusinessLogicException(ExceptionCode.INVALID_CLUB_MEMBER_STATUS);
		}
	}
}
