package com.codestates.azitserver.global.config;

import static org.springframework.security.config.Customizer.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
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

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String clientSecret;

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

				/*======아래 도메인에 맞는 권한 설정을 부여해야합니다.======*/
				.antMatchers(HttpMethod.GET, "api/clubs/recommend/**").authenticated()  // 회원 추천 아지트 조회
				.antMatchers(HttpMethod.GET, "api/clubs/members/**").authenticated()  // 특정 회원이 참여한 아지트 조회
				.antMatchers(HttpMethod.GET, "api/clubs/**").permitAll()  // 그 외 아지트 조회
				.anyRequest().permitAll())  // TODO : 개발하기 편하게 임시로 .permitAll() 설정 -> 나중에 .authenticated() 바꾸기

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
				jwtTokenizer, redisUtils);

			jwtAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");

			jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
			jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

			JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

			builder.addFilter(jwtAuthenticationFilter)
				.addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
		}
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		var clientRegistration = clientRegistration();

		return new InMemoryClientRegistrationRepository(clientRegistration);
	}

	private ClientRegistration clientRegistration() {
		return CommonOAuth2Provider
			.GOOGLE
			.getBuilder("google")
			.clientId(clientId)
			.clientSecret(clientSecret)
			.build();
	}
}