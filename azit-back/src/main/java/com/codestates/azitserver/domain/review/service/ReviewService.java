package com.codestates.azitserver.domain.review.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.club.service.ClubMemberService;
import com.codestates.azitserver.domain.club.service.ClubService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.repository.MemberRepository;
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
	private final MemberRepository memberRepository;
	private final MemberService memberService;
	private final ClubService clubService;
	private final ClubMemberService clubMemberService;

	public Review createReview(Member member, Review review) {
		// 리뷰 작성은 본인만 가능합니다.
		verifyMember(member, review.getReviewer().getMemberId());

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

		// 리뷰를 작성하면 작성한 리뷰 대상자는 평판이 1 올라갑니다.
		reviewee.setReputation(reviewee.getReputation() + 1);
		memberRepository.save(reviewee);

		return reviewRepository.save(review);
	}

	public Review updateReviewStatus(Member member, Long reviewId, Boolean reviewStatus) {
		Review review = findReviewById(reviewId);

		// 리뷰 대상자는 해당 리뷰를 숨김처리 할 수 있습니다.
		verifyMember(member, review.getReviewee().getMemberId());
		review.setReviewStatus(reviewStatus);

		return reviewRepository.save(review);
	}

	public Review findReviewById(Long reviewId) {
		Optional<Review> optionalReview = reviewRepository.findById(reviewId);
		return optionalReview.orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));
	}

	public Page<Review> findReviewByRevieweeId(Member member, Long revieweeId, int page, int size) {
		// 조회 요청자가 본인이 아닐때(비로그인 포함)에는 숨김처리 안 한 것들만 조회합니다.
		// 조회 요청자가 본인일때는 숨김처리된 것들까지 조회합니다.
		if (member == null ||!member.getMemberId().equals(revieweeId)) {
			return reviewRepository.findAllReviewsByRevieweeIdWithoutHide(revieweeId,
				PageRequest.of(page, size, Sort.by("createdAt").descending()));
		} else {
			return reviewRepository.findAllReviewsByRevieweeId(revieweeId,
				PageRequest.of(page, size, Sort.by("createdAt").descending()));
		}
	}

	// *** verification ***//
	private void verifyMember(Member member, Long memberId) {
		if (!member.getMemberId().equals(memberId)) {
			throw new BusinessLogicException(ExceptionCode.MEMBER_VERIFICATION_FAILED);
		}
	}

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
