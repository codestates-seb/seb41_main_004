package com.codestates.azitserver.domain.auth.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

// 	//비밀번호 인증
// 	@PostMapping("/{member-id}/passwords/matchers")
//
// 	//비밀번호 찾기
// 	@PostMapping("/{member-id}/passwords")

	//비밀번호 변경patch
	@PatchMapping("/{member-id}/passwords")
	public

// 	//로그아웃
// 	@PostMapping("/logout")
//
// }
