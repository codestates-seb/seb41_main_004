package com.codestates.azitserver.domain.member.controller;

import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.mapper.MemberMapper;
import com.codestates.azitserver.domain.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(value = "/api/members", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    //회원 생성
    //인증 부분 테스트를 위해 회원 생성 부분만 먼저 작성함
    @PostMapping
    public ResponseEntity postMember(@RequestBody @Valid MemberDto.Post memberPostDto) {
        Member member = memberMapper.memberPostDtoToMember(memberPostDto);
        //Test
        member.setMemberId(1L);
        member.setAvatar_image_id(1L);
        member.setGender(Member.Gender.MALE);
        member.setReputation(10);
        member.setMemberStatus(Member.MemberStatus.ACTIVE);
        member.setBirthYear(1999);
        //여기까지 Test

        Member response = memberService.createMember(member);

        return new ResponseEntity<>(memberMapper.memberToMemberResponseDto(response), HttpStatus.CREATED);}

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
