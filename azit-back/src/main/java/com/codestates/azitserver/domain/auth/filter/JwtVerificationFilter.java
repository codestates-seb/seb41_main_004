package com.codestates.azitserver.domain.auth.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codestates.azitserver.domain.auth.jwt.JwtTokenizer;
import com.codestates.azitserver.domain.auth.userdetails.MemberDetails;
import com.codestates.azitserver.domain.auth.userdetails.MemberDetailsService;
import com.codestates.azitserver.domain.auth.utils.CustomAuthorityUtils;
import com.codestates.azitserver.global.exception.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
	private final JwtTokenizer jwtTokenizer;
	private final CustomAuthorityUtils authorityUtils;
	private final MemberDetailsService memberDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			Map<String, Object> claims = verifyJws(request);
			setAuthenticationToContext(claims, request);
		} catch (SignatureException se) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			ErrorResponse errorResponse = new ErrorResponse(403, "Signature unauthorized");
			new ObjectMapper().writeValue(response.getWriter(), errorResponse);
		} catch (ExpiredJwtException ee) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			ErrorResponse errorResponse = new ErrorResponse(401, "Expired JWT");
			new ObjectMapper().writeValue(response.getWriter(), errorResponse);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			ErrorResponse errorResponse = new ErrorResponse(400, "Bad request");
			new ObjectMapper().writeValue(response.getWriter(), errorResponse);
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		// authorization header 값이 null 이거나 bearer로 시작하지 않으면 자격증명 안하고 다음 filter로 넘어감
		String authorization = request.getHeader("Authorization");

		return authorization == null || !authorization.startsWith("Bearer");
	}

	private Map<String, Object> verifyJws(HttpServletRequest request) {
		// bearer 제거한 claims 리턴
		String jws = request.getHeader("Authorization").replace("Bearer ", "");
		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
		Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

		return claims;
	}

	private void setAuthenticationToContext(Map<String, Object> claims, HttpServletRequest request) {
		// authentication 객체를 securityContext에 저장
		String email = (String)claims.get("email");

		MemberDetails memberDetails = (MemberDetails)memberDetailsService.loadUserByUsername(email);
		List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(memberDetails,
			null, authorities);

		// request에 MemberDetails를 담아서 컨트롤러에게 전달합니다.
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}