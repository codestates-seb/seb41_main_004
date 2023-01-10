package com.codestates.azitserver.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    //회원 생성
    @PostMapping
    public ResponseEntity postMember() { return ResponseEntity.created(null).build();}

    //전체 회원 조회
    @GetMapping
    public ResponseEntity getMembers() { return ResponseEntity.ok(null);}

    //개별 회원 조회
    @GetMapping("/{member-id}")
    public ResponseEntity getMember() { return ResponseEntity.ok(null);}

    //회원 수정
    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember() { return ResponseEntity.ok(null);}

    //회원 삭제(탈퇴)
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember() { return ResponseEntity.noContent().build();}

    //팔로우, 언팔로우
    @PostMapping("/follows/{member-id}")
    public ResponseEntity followMember() { return ResponseEntity.created(null).build();}

    //회원 신고
    @PostMapping("/reports/{member-id}")
    public ResponseEntity reportMember() { return ResponseEntity.created(null).build();}

    //회원 차단
    @PostMapping("/blocks/{member-id}")
    public ResponseEntity blockMember() { return  ResponseEntity.created(null).build();}
}
