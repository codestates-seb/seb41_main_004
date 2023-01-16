package com.codestates.azitserver.domain.auth.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.codestates.azitserver.domain.auth.dto.LoginDto;
import com.codestates.azitserver.domain.auth.jwt.JwtTokenizer;
import com.codestates.azitserver.domain.auth.userdetails.MemberDetails;
import com.codestates.azitserver.domain.auth.utils.RedisUtils;
import com.codestates.azitserver.domain.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private final JwtTokenizer jwtTokenizer;
	private final RedisUtils redisUtils;

	// 인증 시도하는 메서드
	@SneakyThrows
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		ObjectMapper objectMapper = new ObjectMapper();
		// 클라이언트에서 받은 email, password 값을 LoginDto로 역직렬화
		LoginDto.Post loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.Post.class);

		// email, password 값을 포함한 토큰 생성
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			loginDto.getEmail(), loginDto.getPassword());

		// authenticationManager한테 인증 처리 위임
		return authenticationManager.authenticate(authenticationToken);
	}

	// 인증 되면 토큰 발급
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws ServletException, IOException {
		MemberDetails memberDetails = (MemberDetails)authResult.getPrincipal();
		Member member = memberDetails.getMember();

		// accessToken 생성
		String accessToken = delegateAccessToken(member);

		// refreshToken 생성
		Map.Entry<String, Date> entry = delegateRefreshToken(member).entrySet().iterator().next();
		String refreshToken = entry.getKey();
		Long expiration = entry.getValue().getTime();

		// redis에 refreshToken, 멤버정보, 만료시간 전달
		redisUtils.setData(
			refreshToken,
			member.getEmail(),
			expiration
		);

		// 유저정보 만들기
		LoginDto.Response responseDto = new LoginDto.Response();
		responseDto.setEmail(member.getEmail());
		responseDto.setNickname(member.getNickname());
		responseDto.setProfileUrl(member.getFileInfo().getFileUrl());

		ObjectMapper objectMapper = new ObjectMapper();
		String info = objectMapper.writeValueAsString(responseDto);

		// response header에 토큰, 유저정보 담아서 전달
		response.setHeader("Authorization", "Bearer " + accessToken);
		response.setHeader("Refresh", refreshToken);
		response.getWriter().write(info);
	}

	// AccessToken 생성
	private String delegateAccessToken(Member member) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", member.getEmail());
		claims.put("roles", member.getRoles());

		String subject = member.getEmail(); // 제목 지정
		Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes()); // 만료시간 설정

		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(
			jwtTokenizer.getSecretKey()); // secretKey 인코딩

		String accessToken = // 위 정보들로 accessToken 생성
			jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

		return accessToken;
	}

	// RefreshToken 생성
	private Map<String, Date> delegateRefreshToken(Member member) {
		String subject = member.getEmail();
		Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

		String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

		Map<String, Date> refreshInfo = new HashMap<>();
		refreshInfo.put(refreshToken, expiration);

		return refreshInfo;
	}
}