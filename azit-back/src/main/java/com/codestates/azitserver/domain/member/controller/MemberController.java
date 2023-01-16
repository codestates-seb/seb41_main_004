package com.codestates.azitserver.domain.member.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.mapper.MemberMapper;
import com.codestates.azitserver.domain.member.service.MemberService;
import com.codestates.azitserver.global.dto.SingleResponseDto;

@RestController
@RequestMapping(value = "/api/members", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class MemberController {
	private static final String MEMBER_DEFAULT_URL = "/api/members";
	private final MemberService memberService;
	private final MemberMapper memberMapper;

	public MemberController(MemberService memberService, MemberMapper memberMapper) {
		this.memberService = memberService;
		this.memberMapper = memberMapper;
	}

	//회원 생성
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> postMember(@RequestPart(name = "data") @Valid MemberDto.Post memberPostDto,
		@RequestPart(name = "image", required = false) MultipartFile profileImage) {

		Member tempMember = memberMapper.memberPostDtoToMember(memberPostDto);
		// 'password 한번 더' 절차
		memberService.passwordConfirmer(memberPostDto);

		Member createdMember = memberService.createMember(tempMember, profileImage);
		MemberDto.Response response = memberMapper.memberToMemberResponseDto(createdMember);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

	// 회원 프로필 이미지 수정
	@PostMapping(value = "/{member-id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> postMemberImage(@Positive @PathVariable("member-id") Long memberId,
		@RequestPart(name = "image", required = false) MultipartFile profileImage) {

		Member member = memberService.profileImageCombiner(memberId, profileImage);

		MemberDto.Response response = memberMapper.memberToMemberResponseDto(member);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	//전체 회원 조회
	@GetMapping
	public ResponseEntity getMembers(@RequestParam(value = "page", defaultValue = "1") @Positive int page,
		@RequestParam(value = "size", defaultValue = "10") @Positive int size) {
		List<Member> members = memberService.getMembers(page, size).getContent();

		return new ResponseEntity<>(memberMapper.membersToMemberResponseDtos(members), HttpStatus.OK);
	}

	//개별 회원 조회
	@GetMapping("/{member-id}")
	public ResponseEntity getMember(@Positive @PathVariable("member-id") long memberId) {

		Member member = memberService.getMemberById(memberId);
		MemberDto.Response response = memberMapper.memberToMemberResponseDto(member);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	//회원 수정
	@PatchMapping("/{member-id}")
	public ResponseEntity patchMember(@Positive @PathVariable("member-id") Long memberId,
		@RequestBody @Valid MemberDto.Patch memberPatchDto) {
		Member member = memberMapper.memberPatchDtoToMember(memberPatchDto);
		member.setMemberId(memberId);
		// 'password 한번 더' 절차
		memberService.passwordConfirmer(memberPatchDto);

		Member updatedMember = memberService.patchMember(member);
		MemberDto.Response response = memberMapper.memberToMemberResponseDto(member);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	//회원 삭제(탈퇴)
	@DeleteMapping("/{member-id}")
	public ResponseEntity deleteMember(@Positive @PathVariable("member-id") Long memberId) {
		Member member = memberService.deleteMember(memberId);
		MemberDto.Response response = memberMapper.memberToMemberResponseDto(member);
		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.ACCEPTED);
	}

	//TODO 팔로우, 언팔로우
	@PostMapping("/follows/{member-id}")
	public ResponseEntity followMember() {
		return ResponseEntity.created(null).build();
	}

	//TODO 회원 신고
	@PostMapping("/reports/{member-id}")
	public ResponseEntity reportMember() {
		return ResponseEntity.created(null).build();
	}

	//TODO 회원 차단
	@PostMapping("/blocks/{member-id}")
	public ResponseEntity blockMember() {
		return ResponseEntity.created(null).build();
	}
}
