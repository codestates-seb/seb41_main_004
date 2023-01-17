package com.codestates.azitserver.domain.club.controller;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.codestates.azitserver.domain.club.dto.ClubMemberDto;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.club.mapper.ClubMemberMapper;
import com.codestates.azitserver.domain.club.service.ClubMemberService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.stub.ClubMemberStubData;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = ClubMemberController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ClubMemberControllerTest implements ClubMemberControllerTestHelper {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	ClubMemberService clubMemberService;

	@MockBean
	ClubMemberMapper mapper;

	Map<String, Long> resources = new HashMap<>();
	ClubMember clubMember;
	ClubMemberDto.Signup signup;
	ClubMemberDto.Patch patch;
	ClubMemberDto.Response response;
	Page<ClubMember> clubMemberPage;

	@BeforeEach
	void beforeEach() {
		// Make stub data
		resources.put("club-id", 1L);
		clubMember = ClubMemberStubData.getDefaultClubMember();
		signup = ClubMemberStubData.getClubMemberSignup();
		patch = ClubMemberStubData.getClubMemberPatch();
		response = ClubMemberStubData.getClubMemberDtoResponse();
		clubMemberPage = ClubMemberStubData.getClubMemberPage();

	}

	@Test
	void postClubMember() throws Exception {
		// given
		String content = objectMapper.writeValueAsString(signup);

		doNothing().when(clubMemberService).verifyMember(Mockito.any(Member.class), Mockito.anyLong());
		given(clubMemberService.signup(Mockito.any(), Mockito.anyLong(), Mockito.anyString()))
			.willReturn(clubMember);
		given(mapper.clubMemberToClubMemberDtoResponse(Mockito.any(ClubMember.class))).willReturn(response);

		// when
		ResultActions actions = mockMvc.perform(
			post("/api/clubs/{club-id}/signups", 1L)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.header("Authorization", "Required JWT access token")
				.content(content));

		// then
		actions.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.clubMemberId").value(1))
			.andDo(getDefaultDocument("post-clubMember",
				requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
				pathParameters(List.of(
					parameterWithName("club-id").description("아지트 고유 식별자"))),
				ClubMemberFieldDescriptor.getSignupRequestFieldsSnippet(),
				ClubMemberFieldDescriptor.getSingleResponseFieldSnippet()
			));
	}

	@Test
	void getClubMember() throws Exception {
		// given
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", "1");
		queryParams.add("size", "10");

		given(clubMemberService.getAllClubMemberByClubId(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
			.willReturn(clubMemberPage);
		given(mapper.clubMemberToClubMemberDtoResponse(Mockito.anyList())).willReturn(List.of(response));

		// when
		ResultActions actions = mockMvc.perform(
			getRequestBuilder(getClubMemberUrl("signups"), queryParams, 1L)
				.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument("get-club-member",
				requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
				requestParameters(
					List.of(
						parameterWithName("page").description("Page 번호"),
						parameterWithName("size").description("Page 크기")
					)),
				pathParameters(List.of(
					parameterWithName("club-id").description("아지트 고유 식별자"))),
				ClubMemberFieldDescriptor.getMultiResponseFieldSnippet()
			));
	}

	@Test
	void patchClubMembers() throws Exception {
		// given
		Long clubId = 1L;
		Long memberId = 1L;
		String content = objectMapper.writeValueAsString(patch);

		doNothing().when(clubMemberService)
			.updateMemberStatus(Mockito.any(Member.class), Mockito.anyLong(), Mockito.anyLong(), Mockito.any());

		// when
		ResultActions actions = mockMvc.perform(
			patchRequestBuilder(getClubMemberUrl("signups", "{member-id}"), content, clubId, memberId)
				.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isAccepted())
			.andDo(getDefaultDocument("patch-club-members",
				requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
				pathParameters(List.of(
					parameterWithName("club-id").description("아지트 고유 식별자"),
					parameterWithName("member-id").description("회원 고유 식별자"))
				),
				ClubMemberFieldDescriptor.getPatchRequestFieldsSnippet()
			));
	}

	@Test
	void kickClubMembers() throws Exception {
		// given
		Long clubId = 1L;
		Long memberId = 1L;

		doNothing().when(clubMemberService)
			.kickMember(Mockito.any(Member.class), Mockito.anyLong(), Mockito.anyLong());

		// when
		ResultActions actions = mockMvc.perform(
			patchRequestBuilder(getClubMemberUrl("kicks", "{member-id}"), "", clubId, memberId)
				.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isNoContent())
			.andDo(getDefaultDocument("kick-club-members",
				requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
				pathParameters(List.of(
					parameterWithName("club-id").description("아지트 고유 식별자"),
					parameterWithName("member-id").description("회원 고유 식별자"))
				)
			));
	}
}