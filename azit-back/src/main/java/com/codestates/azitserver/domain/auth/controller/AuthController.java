package com.codestates.azitserver.domain.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.azitserver.domain.auth.dto.AuthDto;
import com.codestates.azitserver.domain.auth.service.AuthService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.global.annotation.LoginMember;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	// @PostMapping("/refresh/passwords/email")
	// public ResponseEntity sendEmail (@RequestBody AuthDto.sendEmail request) {
	// 	// request(email)을 service로 보낸다.
	// 	// 200 OK만 보내기
	// 	return null;
	// }
	//
	// @PostMapping("/refresh/passwords")
	// public ResponseEntity sendPassword (@RequestBody AuthDto.sendPassword request) {
	// 	// request(email, authNumber)을 service로 보낸다.
	// 	// 200 OK만 보내기
	// 	return null;
	// }

	// 비밀번호 인증(password 변경 페이지로 가기 전)
	@PostMapping("/{member-id:[0-9]+}/passwords/matchers")
	public ResponseEntity matchPassword(@Positive @PathVariable("member-id") Long memberId,
		@RequestBody AuthDto.MatchPassword request) {
		boolean result = authService.passwordMatcher(memberId, request);

		if (result) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	//비밀번호 변경
	@PatchMapping("/{member-id:[0-9]+}/passwords")
	public ResponseEntity patchPassword(@Positive @PathVariable("member-id") Long memberId,
		@RequestBody AuthDto.PatchPassword request,
		@LoginMember Member member) {
		authService.updatePassword(memberId, request, member);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/reIssue")
	public ResponseEntity reIssueToken(HttpServletRequest request, HttpServletResponse response) {
		authService.reIssueToken(request, response);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 	//로그아웃
	// 	@PostMapping("/logout")
	//
	// }
}
