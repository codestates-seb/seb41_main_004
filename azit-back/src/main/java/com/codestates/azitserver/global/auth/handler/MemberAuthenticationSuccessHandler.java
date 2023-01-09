package com.codestates.azitserver.global.auth.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication) throws IOException {
		log.info("# Authenticated successfully!");
	}
}