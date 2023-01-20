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
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
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
import com.codestates.azitserver.domain.auth.handler.OAuthSuccessHandler;
import com.codestates.azitserver.domain.auth.jwt.JwtTokenizer;
import com.codestates.azitserver.domain.auth.userdetails.MemberDetailsService;
import com.codestates.azitserver.domain.auth.utils.CustomAuthorityUtils;
import com.codestates.azitserver.domain.auth.utils.RedisUtils;
import com.codestates.azitserver.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	private final JwtTokenizer jwtTokenizer;
	private final CustomAuthorityUtils authorityUtils;
	private final MemberRepository memberRepository;
	private final RedisUtils redisUtils;
	private final MemberDetailsService memberDetailsService;

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
			.userDetailsService(memberDetailsService)
			.authorizeHttpRequests(authorize -> authorize
				/*======h2, docs======*/
				.antMatchers("/h2/**").permitAll() // h2
				.antMatchers("/docs/index.html").permitAll() // docs
				.antMatchers(HttpMethod.GET, "/api/errors").permitAll() // error
				.antMatchers(HttpMethod.GET, "/health-check").permitAll()  // health-check
				.antMatchers(HttpMethod.GET, "/opt/codedeploy-agent/**").permitAll()  // 개발서버 static 장소(System.getProperty("user.dir"))

				/*======아래 도메인에 맞는 권한 설정을 부여해야합니다.======*/
				/*======clubs======*/
				.antMatchers(HttpMethod.GET, "/api/clubs/members/**").authenticated()  // 특정 회원이 참여한 아지트 조회
				.antMatchers("/api/clubs/reports/**").permitAll()  // 아지트 신고
				.antMatchers(HttpMethod.GET, "/api/clubs/**").permitAll()  // 그 외 아지트 조회

				/*==========member==========*/
				.antMatchers(HttpMethod.GET, "/api/members/**").authenticated() //특정 회원 조회
				.antMatchers(HttpMethod.PATCH, "/api/members/**").authenticated() // 회원 정보 수정
				.antMatchers(HttpMethod.DELETE, "/api/members/**").authenticated() // 회원 탈퇴(삭제)
				.antMatchers(HttpMethod.GET, "/api/members").authenticated() // 전체 회원 조회 //TODO (미구현(error))
				.antMatchers(HttpMethod.POST, "/api/members").permitAll()
				.antMatchers(HttpMethod.POST, "/api/members/**").permitAll()

				/*==========category==========*/
				.antMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/categories").permitAll()


				/*======auth======*/
				.antMatchers(HttpMethod.POST, "/api/auth/login").permitAll() // 로그인
				.antMatchers(HttpMethod.POST, "/api/auth/*/passwords").permitAll() // 비밀번호 찾기

				.anyRequest().authenticated())

			.oauth2Login(oauth2 -> oauth2
				.successHandler(new OAuthSuccessHandler(jwtTokenizer, memberRepository, redisUtils)));

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
			"https://azit-server-s3.s3.ap-northeast-2.amazonaws.com",
			"http://azit-front.s3-website.ap-northeast-2.amazonaws.com",
			"https://azit-front.s3-website.ap-northeast-2.amazonaws.com"));
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
				jwtTokenizer, redisUtils);

			jwtAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");

			jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
			jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

			JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, memberDetailsService);

			builder.addFilter(jwtAuthenticationFilter)
				.addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class)
				.addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
		}
	}
}