package com.codestates.azitserver.domain.auth.handler;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.codestates.azitserver.domain.auth.dto.response.AuthResponseDto;
import com.codestates.azitserver.domain.auth.jwt.JwtTokenizer;
import com.codestates.azitserver.domain.auth.utils.RedisUtils;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.repository.MemberRepository;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JwtTokenizer jwtTokenizer;
	private final MemberRepository memberRepository;
	private final RedisUtils redisUtils;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		var oAuth2User = (OAuth2User)authentication.getPrincipal();
		String email = String.valueOf(oAuth2User.getAttributes().get("email"));
		String nickname = String.valueOf(oAuth2User.getAttributes().get("name"));

		// email로 존재하는 회원인지 확인해서 존재하면 토큰만 발급
		if (memberRepository.findByEmail(email).isPresent()) {
			Optional<Member> optionalMember = memberRepository.findByEmail(email);
			Member member = optionalMember.orElseThrow(() -> new BusinessLogicException(
				ExceptionCode.MEMBER_NOT_FOUND));

			delegateTokens(member, request, response);
		}
		// 존재하지 않는 회원이면 회원 email, nickname 정보 response로 담아서 보내기 -> 회원 추가정보 페이지로 가서 가입 진행
		else {
			AuthResponseDto.ResponseWithProfile responseWithProfileDto = new AuthResponseDto.ResponseWithProfile();

			responseWithProfileDto.setEmail(email);
			responseWithProfileDto.setNickname(nickname);

			ObjectMapper objectMapper = new ObjectMapper();
			String info = objectMapper.writeValueAsString(responseWithProfileDto);

			response.getWriter().write(info);
		}
	}

	//토큰 발급 메서드
	private void delegateTokens(Member member, HttpServletRequest request, HttpServletResponse response) throws
		IOException {
		String accessToken = delegateAccessToken(member); // Access Token 생성
		Map.Entry<String, Date> entry = delegateRefreshToken(member).entrySet().iterator().next();
		String refreshToken = entry.getKey();
		Long expiration = entry.getValue().getTime();

		redisUtils.setData(
			refreshToken,
			member.getEmail(),
			expiration
		);

		// 유저정보 만들기
		AuthResponseDto.ResponseWithProfile responseWithProfileDto = new AuthResponseDto.ResponseWithProfile();
		responseWithProfileDto.setMemberId(member.getMemberId());
		responseWithProfileDto.setEmail(member.getEmail());
		responseWithProfileDto.setNickname(member.getNickname());
		try {
			responseWithProfileDto.setProfileUrl(member.getFileInfo().getFileUrl());
		} catch (NullPointerException e) {
			log.warn("Profile image is null:{}", e.getLocalizedMessage());
		}

		ObjectMapper objectMapper = new ObjectMapper();
		String info = objectMapper.writeValueAsString(responseWithProfileDto);

		// response header에 토큰, 유저정보 담아서 전달
		response.setHeader("Authorization", "Bearer " + accessToken);
		response.setHeader("Refresh", refreshToken);
		response.getWriter().write(info);

		redirect(request, response, accessToken, refreshToken);
	}

	private void redirect(HttpServletRequest request, HttpServletResponse response,
		String accessToken, String refreshToken)
		throws IOException {

		String uri = createURI(accessToken, refreshToken).toString();
		getRedirectStrategy().sendRedirect(request, response, uri);
	}

	private String delegateAccessToken(Member member) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", member.getEmail());
		claims.put("roles", member.getRoles());

		String subject = member.getEmail();
		Date expiration = jwtTokenizer.getTokenExpiration(
			jwtTokenizer.getAccessTokenExpirationMinutes());

		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(
			jwtTokenizer.getSecretKey());

		String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration,
			base64EncodedSecretKey);

		return accessToken;
	}

	private Map<String, Date> delegateRefreshToken(Member member) {
		String subject = member.getEmail();
		Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

		String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

		Map<String, Date> refreshInfo = new HashMap<>();
		refreshInfo.put(refreshToken, expiration);

		return refreshInfo;
	}

	private URI createURI(String accessToken, String refreshToken) {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("Authorization", "Bearer" + accessToken);
		queryParams.add("Refresh", refreshToken);

		return UriComponentsBuilder
			.newInstance()
			.scheme("http")
			.host("ec2-13-209-243-35.ap-northeast-2.compute.amazonaws.com")
			.port(8080)
			.path("/receive-token.html")
			.queryParams(queryParams)
			.build()
			.toUri();
	}
}