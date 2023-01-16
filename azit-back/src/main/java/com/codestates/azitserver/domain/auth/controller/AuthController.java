package com.codestates.azitserver.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.azitserver.domain.auth.dto.LoginDto;
import com.codestates.azitserver.domain.auth.service.AuthService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.global.annotation.LoginMember;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	// 	//비밀번호 찾기
	// 	@PostMapping("/{member-id}/passwords")

	// 비밀번호 인증(password 변경 페이지로 가기 전)
	@PostMapping("/{member-id}/passwords/matchers")
	public ResponseEntity matchPassword(@PathVariable("member-id") Long memberId,
		@RequestBody LoginDto.MatchPassword request) {
		boolean result = authService.passwordMatcher(memberId, request);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	//비밀번호 변경
	@PatchMapping("/{member-id}/passwords")
	public ResponseEntity patchPassword(@PathVariable("member-id") Long memberId,
		@RequestBody LoginDto.PatchPassword request,
		@LoginMember Member member) {
		authService.updatePassword(memberId, request, member);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 	//로그아웃
	// 	@PostMapping("/logout")
	//
	// }
}
