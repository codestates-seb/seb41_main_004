package com.codestates.azitserver.global.config;

import static org.springframework.security.config.Customizer.*;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.codestates.azitserver.domain.auth.filter.JwtAuthenticationFilter;
import com.codestates.azitserver.domain.auth.filter.JwtVerificationFilter;
import com.codestates.azitserver.domain.auth.handler.MemberAccessDeniedHandler;
import com.codestates.azitserver.domain.auth.handler.MemberAuthenticationEntryPoint;
import com.codestates.azitserver.domain.auth.handler.MemberAuthenticationFailureHandler;
import com.codestates.azitserver.domain.auth.handler.MemberAuthenticationSuccessHandler;
import com.codestates.azitserver.domain.auth.jwt.JwtTokenizer;
import com.codestates.azitserver.domain.auth.utils.CustomAuthorityUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	private final JwtTokenizer jwtTokenizer;
	private final CustomAuthorityUtils authorityUtils;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.headers().frameOptions().sameOrigin()
			.and()
			.csrf().disable()
			.cors(withDefaults())
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			.exceptionHandling()
			.authenticationEntryPoint(new MemberAuthenticationEntryPoint())
			.accessDeniedHandler(new MemberAccessDeniedHandler())
			.and()
			.apply(new CustomFilterConfigurer())
			.and()
			.authorizeHttpRequests(authorize -> authorize
				/*======h2, docs======*/
				.antMatchers(HttpMethod.GET, "/h2/**").permitAll() // h2
				.antMatchers("/docs/index.html").permitAll() // docs
				/*======auth======*/
				.antMatchers(HttpMethod.POST, "/auth/login").permitAll() // 로그인 누구나 가능
				.antMatchers(HttpMethod.POST, "/auth/logout").hasRole("USER") // 로그아웃 유저 가능
				.antMatchers(HttpMethod.POST, "/**/passwords").permitAll() // 비밀번호 찾기 유저 가능
				.antMatchers(HttpMethod.POST, "/**/passwords/matchers").hasRole("USER") // 비밀번호 인증 유저 가능
				.antMatchers(HttpMethod.PATCH, "/**/passwords").hasAnyRole("USER", "ADMIN") // 비밀번호 변경 유저 가능(랜덤 비밀번호 발생 시 admin 권한 필요)
				/*======member======*/
				.antMatchers(HttpMethod.POST, "/members").permitAll() // 회원 생성 누구나 가능
				.antMatchers(HttpMethod.PATCH, "/members/**").hasRole("USER") // 회원 수정 유저 가능
				.antMatchers(HttpMethod.GET, "/members").hasRole("ADMIN") // 전체 회원 조회 관리자 가능
				.antMatchers(HttpMethod.GET, "/members/**").hasAnyRole("USER", "ADMIN") // 개별 회원 조회 유저, 관리자 가능
				.antMatchers(HttpMethod.DELETE, "/members/**").hasAnyRole("USER", "ADMIN") // 회원 탈퇴 유저, 관리자 가능
				.antMatchers(HttpMethod.POST, "/members/follows/**").hasRole("USER") // 팔로우 유저 가능
				.antMatchers(HttpMethod.POST, "/members/reports/**").hasRole("USER") // 신고 유저 가능
				.antMatchers(HttpMethod.POST, "/members/blocks/**").hasRole("USER") // 차단 유저 가능
				/*======club======*/
				.antMatchers(HttpMethod.POST, "/clubs").hasRole("USER") // 아지트 생성 유저 가능
				.antMatchers(HttpMethod.GET, "/clubs/recommend/**").hasRole("USER") // 추천 아지트 조회 유저 가능
				.antMatchers(HttpMethod.GET, "/clubs/members/**").hasRole("USER") // 특정 유저 아지트 조회 유저 가능
				.antMatchers(HttpMethod.GET, "/clubs/**").permitAll() // 아지트 조회, 검색 누구나 가능
				.antMatchers(HttpMethod.PATCH, "/clubs/**").hasRole("USER") // 아지트 수정 호스트 가능
				.antMatchers(HttpMethod.DELETE, "/clubs/**").hasRole("USER") // 아지트 삭제 호스트 가능
				.antMatchers(HttpMethod.POST, "/clubs/reports/**").hasRole("USER") // 아지트 신고 유저 가능
				.antMatchers(HttpMethod.POST, "/**/signups").hasRole("USER") // 아지트 참여 신청 유저 가능
				.antMatchers(HttpMethod.POST, "/**/signups/**").hasRole("USER") // 아지트 참여 승인/거부 호스트 가능
				.antMatchers(HttpMethod.POST, "/**/kicks/**").hasRole("USER") // 아지트 강퇴 호스트 가능
				/*======review======*/
				.antMatchers(HttpMethod.POST, "/reviews").hasRole("USER") // 리뷰 유저 가능
				.antMatchers(HttpMethod.PATCH, "/reviews/**").hasRole("USER") // 리뷰 상태변경 유저 가능
				.antMatchers(HttpMethod.GET, "/reviews").hasRole("USER") // 리뷰 전체 조회 유저 가능

				.anyRequest().permitAll()
			);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(List.of(
			"http://localhost:3000",
			"https://localhost:3000",
			"http://azit-server-s3.s3.ap-northeast-2.amazonaws.com",
			"https://azit-server-s3.s3.ap-northeast-2.amazonaws.com"));
		configuration.setAllowedMethods(List.of("POST", "GET", "PATCH", "DELETE", "OPTIONS", "HEAD"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setExposedHeaders(List.of("Authorization", "Refresh"));  // https://ahndding.tistory.com/4

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
		@Override
		public void configure(HttpSecurity builder) throws Exception {
			AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

			JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager,
				jwtTokenizer);

			jwtAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");

			jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
			jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

			JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

			builder.addFilter(jwtAuthenticationFilter)
				.addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
		}
	}
}