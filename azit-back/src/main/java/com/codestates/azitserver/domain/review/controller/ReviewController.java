package com.codestates.azitserver.domain.review.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    //리뷰 작성
    @PostMapping
    public ResponseEntity postReview() { return ResponseEntity.created(null).build(); }

    //리뷰 상태변경
    @PatchMapping("/{review-id}")
    public ResponseEntity patchReview() { return ResponseEntity.ok(null); }

    //특정 회원에 대한 리뷰 전체 조회
    @GetMapping
    public ResponseEntity findAllByMember() { return ResponseEntity.ok(null); }

}
