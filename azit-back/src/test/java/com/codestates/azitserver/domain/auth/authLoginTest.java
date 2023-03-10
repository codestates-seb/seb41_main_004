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
import org.junit.jupiter.api.TestInstance;
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

import com.codestates.azitserver.domain.auth.dto.AuthDto;
import com.codestates.azitserver.domain.fileInfo.entity.FileInfo;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.repository.MemberRepository;
import com.google.gson.Gson;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class authLoginTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private Gson gson;

	@BeforeEach
	@DisplayName("???????????? ?????? ??????")
	public void init() {
		// ?????? ??????
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName("?????????????????????.png");
		fileInfo.setFileUrl("/folder1/folder2");
		fileInfo.setCreatedAt(null);
		fileInfo.setLastModifiedAt(null);

		// ???????????? ????????????
		String encodedPassword = passwordEncoder.encode("123456@asdf");

		// ???????????? ?????? ??????
		Member member = Member.builder()
			.fileInfo(fileInfo)
			.email("stubmember01@naver.com")
			.nickname("?????????01")
			.password(encodedPassword)
			.gender(Member.Gender.MALE)
			.birthYear("2001")
			.aboutMe("???????????? ????????????")
			.reputation(10)
			.memberStatus(Member.MemberStatus.ACTIVE)
			.build();

		// ???????????? ?????? ??????
		memberRepository.save(member);
	}

	// ????????? ????????? ??? ?????????????????? ?????? /api/auth/login
	@Test
	@DisplayName("??????????????? ????????? ??????????????? ????????????.")
	public void loginTest() throws Exception {
		// given - LoginDto??? ????????? ????????? json ????????????
		AuthDto.Post loginDto = new AuthDto.Post();
		loginDto.setEmail("stubmember01@naver.com");
		loginDto.setPassword("123456@asdf");
		// loginDto.setEmail("admin_test@hello.com");
		// loginDto.setPassword("1234qwer!@#$");

		String content = gson.toJson(loginDto);

		// when - ????????? api??? ??????
		ResultActions actions =
			mockMvc.perform(
				post("/api/auth/login")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.characterEncoding("UTF-8")
					.with(csrf())
					.content(content)
			);

		// then - ?????????????????? ??????
		actions
			.andExpect(status().isOk())
			.andExpect(header().exists("Authorization"))
			.andExpect(header().exists("Refresh"))
			.andDo(document("login",
				getRequestPreProcessor(),
				getResponsePreProcessor(),
				requestFields(List.of(
					fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("????????????"))),
				responseHeaders(
					headerWithName("Authorization").description("????????? ??????"),
					headerWithName("Refresh").description("???????????? ??????")),
				responseFields(List.of(
					fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("???????????????"),
					fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
					fieldWithPath("nickname").type(JsonFieldType.STRING).description("?????????"),
					fieldWithPath("profileUrl").type(JsonFieldType.STRING).description("????????? ????????? ??????"),
					fieldWithPath("profileImageName").type(JsonFieldType.STRING).description("????????? ????????? ??????")))
			));
	}
}