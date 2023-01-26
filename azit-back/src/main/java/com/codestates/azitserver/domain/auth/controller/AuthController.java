package com.codestates.azitserver.domain.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.azitserver.domain.auth.dto.AuthDto;
import com.codestates.azitserver.domain.auth.dto.response.AuthResponseDto;
import com.codestates.azitserver.domain.auth.service.AuthService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.global.annotation.LoginMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/refresh/passwords/email")
	public ResponseEntity sendAuthNum(@RequestBody AuthDto.SendEmail request) throws Exception {
		authService.sendAuthEmail(request);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/refresh/passwords")
	public ResponseEntity sendPassword(@RequestBody AuthDto.SendPWEmail request) throws Exception {
		authService.resetPassword(request);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 비밀번호 인증(password 변경 페이지로 가기 전)
	@PostMapping("/{member-id:[0-9]+}/passwords/matchers")
	public ResponseEntity matchPassword(@Positive @PathVariable("member-id") Long memberId,
		@RequestBody AuthDto.MatchPassword request) {
		authService.passwordMatcher(memberId, request);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	//비밀번호 변경
	@PatchMapping("/{member-id:[0-9]+}/passwords")
	public ResponseEntity patchPassword(@Positive @PathVariable("member-id") Long memberId,
		@RequestBody AuthDto.PatchPassword request,
		@LoginMember Member member) {
		authService.updatePassword(memberId, request, member);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/re-issue")
	@CrossOrigin(origins = {"http://localhost:3000",
		"http://azit-server-s3.s3.ap-northeast-2.amazonaws.com"}, methods = RequestMethod.POST)
	public ResponseEntity reIssueToken(HttpServletRequest request, HttpServletResponse response,
		@RequestBody AuthDto.SendEmail memberEmail) {
		log.info("controller 요청 들어옴");
		AuthResponseDto.TokenResponse tokenResponse = authService.reIssueToken(request, memberEmail);
		response.setHeader("Authorization", tokenResponse.getAccessToken());
		response.setHeader("Refresh", tokenResponse.getRefreshToken());

		log.info("리스폰스 헤더로 담음");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	// @GetMapping("/re-issue")
	// @CrossOrigin(origins = {"http://localhost:3000",
	// 	"http://azit-server-s3.s3.ap-northeast-2.amazonaws.com"}, methods = RequestMethod.GET)
	// public ResponseEntity reIssueToken(HttpServletRequest request, HttpServletResponse response,
	// 	@RequestParam("email") String email) {
	// 	log.info("controller 요청 들어옴");
	// 	AuthResponseDto.TokenResponse tokenResponse = authService.reIssueToken(request, email);
	// 	response.setHeader("Authorization", tokenResponse.getAccessToken());
	// 	response.setHeader("Refresh", tokenResponse.getRefreshToken());
	//
	// 	log.info("리스폰스 헤더로 담음");
	// 	return new ResponseEntity<>(HttpStatus.OK);
	// }

	//로그아웃
	@PostMapping("/logout")
	public ResponseEntity logout(HttpServletRequest request) {
		authService.logout(request);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
