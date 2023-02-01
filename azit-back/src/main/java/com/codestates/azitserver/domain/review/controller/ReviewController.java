package com.codestates.azitserver.domain.review.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Positive;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.review.dto.ReviewDto;
import com.codestates.azitserver.domain.review.entity.Review;
import com.codestates.azitserver.domain.review.mapper.ReviewMapper;
import com.codestates.azitserver.domain.review.service.ReviewService;
import com.codestates.azitserver.global.annotation.LoginMember;
import com.codestates.azitserver.global.dto.MultiResponseDto;
import com.codestates.azitserver.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;
	private final ReviewMapper mapper;

	//리뷰 작성
	// @PostMapping
	public ResponseEntity<?> postReview(@RequestBody ReviewDto.Post post, @LoginMember Member member) {
		Review postToReview = mapper.reviewDtoPostToReview(post);
		Review review = reviewService.createReview(member, postToReview);
		ReviewDto.Response response = mapper.reviewToReviewDtoResponse(review);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// TODO : 트랜잭션 문제 해결하기 -> 여러 리뷰 작성중 한개라도 성공하지 못하면 나머지 작성 리뷰 또한 롤백 되어야 함
	// 여러개의 리뷰 작성
	@PostMapping
	public ResponseEntity<?> postReviews(@RequestBody List<ReviewDto.Post> posts, @LoginMember Member member) {
		List<ReviewDto.Response> response = new ArrayList<>();
		for (ReviewDto.Post post : posts) {
			ResponseEntity<?> responseEntity = postReview(post, member);
			response.add((ReviewDto.Response)responseEntity.getBody());
		}

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

	//리뷰 상태변경
	@PatchMapping("/{review-id:[0-9]+}")
	public ResponseEntity<?> patchReview(@PathVariable("review-id") Long reviewId, @RequestBody ReviewDto.Patch patch,
		@LoginMember Member member) {
		Review review = reviewService.updateReviewStatus(member, reviewId, patch.getReviewStatus());
		ReviewDto.Response response = mapper.reviewToReviewDtoResponse(review);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	//특정 회원에 대한 리뷰 전체 조회
	@GetMapping("/reviewee/{reviewee-id:[0-9]+}")
	public ResponseEntity<?> findAllByMember(@Positive @RequestParam(name = "page") int page,
		@Positive @RequestParam(name = "size") int size, @PathVariable("reviewee-id") Long revieweeId,
		@LoginMember Member member) {

		Page<Review> reviewPage = reviewService.findReviewByRevieweeId(member, revieweeId, page - 1, size);
		List<Review> reviews = reviewPage.getContent();
		List<ReviewDto.Response> response = mapper.reviewToReviewDtoResponse(reviews);

		return new ResponseEntity<>(new MultiResponseDto<>(response, reviewPage), HttpStatus.OK);
	}
}
