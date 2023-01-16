package com.codestates.azitserver.domain.club.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.codestates.azitserver.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clubs/{club-id}")
@RequiredArgsConstructor
public class ClubMemberController {
	private final ClubMemberService clubMemberService;
	private final ClubMemberMapper mapper;

	@PostMapping("/signups")
	public ResponseEntity<?> postClubMember(@PathVariable("club-id") Long clubId,
		@RequestBody ClubMemberDto.Signup body,
		@AuthenticationPrincipal Member member) {
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
