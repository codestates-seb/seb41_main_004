package com.codestates.azitserver.domain.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.review.dto.ReviewDto;
import com.codestates.azitserver.domain.review.entity.Review;
import com.codestates.azitserver.domain.review.mapper.ReviewMapper;
import com.codestates.azitserver.domain.review.service.ReviewService;
import com.codestates.azitserver.global.annotation.LoginMember;
import com.codestates.azitserver.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper mapper;

    //리뷰 작성
    @PostMapping
    public ResponseEntity<?> postReview(@RequestBody ReviewDto.Post post, @LoginMember Member member) {
        Review postToReview = mapper.reviewDtoPostToReview(post);
        Review review = reviewService.createReview(member, postToReview);
        ReviewDto.Response response = mapper.reviewToReviewDtoResponse(review);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    //리뷰 상태변경
    @PatchMapping("/{review-id:[0-9]+}")
    public ResponseEntity <?>patchReview(@PathVariable("review-id") Long reviewId, @RequestBody ReviewDto.Patch patch,
        @LoginMember Member member) {
        Review review = reviewService.updateReviewStatus(member, reviewId, patch.getReviewStatus());
        ReviewDto.Response response = mapper.reviewToReviewDtoResponse(review);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    //특정 회원에 대한 리뷰 전체 조회
    @GetMapping
    public ResponseEntity findAllByMember() { return ResponseEntity.ok(null); }

}
