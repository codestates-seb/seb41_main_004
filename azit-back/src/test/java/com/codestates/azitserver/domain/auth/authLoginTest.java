package com.codestates.azitserver.domain.auth;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.azitserver.domain.auth.dto.LoginDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.repository.MemberRepository;
import com.google.gson.Gson;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ActiveProfiles("local")
@SpringBootTest
public class authLoginTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private Gson gson;

	// 테스트하기 위해서 Member 만들어주기
	@BeforeEach
	@DisplayName("테스트용 멤버 생성")
	public void init() {
		// 인코딩된 패스워드
		String encodedPassword = passwordEncoder.encode("qwerty1234!");

		// 테스트용 멤버 생성
		Member member = new Member();
		member.setEmail("usertemp@gmail.com");
		member.setPassword(encodedPassword);
		member.setNickname("tempUser");
		member.setRoles(List.of("USER"));

		// 테스트용 멤버 저장
		memberRepository.save(member);
	}

	// 로그인 요청이 잘 이루어지는지 확인 /api/auth/login
	@Test
	public void loginTest() throws Exception {
		// given - LoginDto로 요청값 받으면 json 형식으로
		LoginDto loginDto = new LoginDto();
		loginDto.setEmail("usertemp@gmail.com");
		loginDto.setPassword("qwerty1234!");

		String content = gson.toJson(loginDto);

		// when - 로그인 api에 요청
		ResultActions actions =
			mockMvc.perform(
				post("/api/auth/login")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.characterEncoding("UTF-8")
					.with(csrf())
					.content(content)
			);

		// then - 로그인되는지 확인
		actions
			.andExpect(status().isOk())
			.andExpect(header().exists("Authorization"))
			.andExpect(header().exists("Refresh"))
			.andDo(document("login",
				getRequestPreProcessor(),
				getResponsePreProcessor(),
				requestFields(
					List.of(
						fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
					)
				),
				responseHeaders(
					headerWithName("Authorization").description("액세스 토큰"),
					headerWithName("Refresh").description("리프레시 토큰")
				)));

	}
}
