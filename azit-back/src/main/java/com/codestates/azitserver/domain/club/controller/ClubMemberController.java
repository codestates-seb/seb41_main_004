package com.codestates.azitserver.domain.club.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.azitserver.domain.club.dto.ClubMemberDto;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.club.mapper.ClubMemberMapper;
import com.codestates.azitserver.domain.club.service.ClubMemberService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.global.annotation.LoginMember;
import com.codestates.azitserver.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clubs/{club-id}")
@RequiredArgsConstructor
public class ClubMemberController {
	private final ClubMemberService clubMemberService;
	private final ClubMemberMapper mapper;

	/**
	 * 특정 아지트에 참여 신청을 보내는 컨트롤러입니다.
	 * @param clubId 참여 신청을 보낼 아지트 고유 식별자
	 * @param body 참여 신청을 위해 작성하는 항목
	 * @param member 참여 신청을 하는 현재 로그인한 사용자
	 * @return 참여 신청이 성공하면 참여 신청 정보를 http status화 함께 반환
	 */
	@PostMapping("/signups")
	public ResponseEntity<?> postClubMember(@PathVariable("club-id") Long clubId,
		@RequestBody ClubMemberDto.Signup body,
		@LoginMember Member member) {
		clubMemberService.verifyMember(member, body.getMemberId());
		ClubMember clubMember = clubMemberService.signup(member, clubId, body.getJoinAnswer());

		ClubMemberDto.Response response = mapper.clubMemberToClubMemberDtoResponse(clubMember);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

	@PatchMapping("/signups/{member-id}")
	public ResponseEntity<?> patchClubMemembers() {
		return null;
	}

	@PatchMapping("/kicks/{member-id}")
	public ResponseEntity<?> kickClubMemembers() {
		return null;
	}
}
