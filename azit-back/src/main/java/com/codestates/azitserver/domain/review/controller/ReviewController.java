package com.codestates.azitserver.domain.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codestates.azitserver.domain.review.dto.ReviewDto;
import com.codestates.azitserver.domain.review.entity.Review;
import com.codestates.azitserver.domain.review.mapper.ReviewMapper;
import com.codestates.azitserver.domain.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper mapper;


    //리뷰 작성
    @PostMapping
    public ResponseEntity<?> postReview(@RequestBody ReviewDto.Post post) {
        Review postToReview = mapper.reviewDtoPostToReview(post);
        Review review = reviewService.createReview(postToReview);
        ReviewDto.Response response = mapper.reviewToReviewDtoResponse(review);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //리뷰 상태변경
    @PatchMapping("/{review-id}")
    public ResponseEntity patchReview() { return ResponseEntity.ok(null); }

    //특정 회원에 대한 리뷰 전체 조회
    @GetMapping
    public ResponseEntity findAllByMember() { return ResponseEntity.ok(null); }

}
