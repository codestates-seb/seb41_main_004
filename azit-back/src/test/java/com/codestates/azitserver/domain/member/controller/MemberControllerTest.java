package com.codestates.azitserver.domain.member.controller;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.mapper.MemberMapper;
import com.codestates.azitserver.domain.member.service.MemberService;
import com.codestates.azitserver.domain.stub.MemberStubData;
import com.google.gson.Gson;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MemberController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class MemberControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MemberService memberService;

	@MockBean
	private MemberMapper memberMapper;
	@Autowired
	private Gson gson;

	Member member;
	MemberDto.Post post;
	MemberDto.Patch patch;
	MemberDto.Response response;
	Page<Member> memberPage;

	@BeforeEach
	void beforeEach() {
		member = MemberStubData.stubMember();
		post = MemberStubData.stubMemberDtoPost();
		patch = MemberStubData.stubMemberDtoPatch();
		response = MemberStubData.stubMemberDtoResponse();
		memberPage = MemberStubData.stubMemberPage();
	}

	@Test
	void postMemberTest() throws Exception {
		// given
		given(memberMapper.memberPostDtoToMember(any(MemberDto.Post.class)))
			.willReturn(member);
		given(memberService.createMember(any(Member.class))).willReturn(member);
		given(memberMapper.memberToMemberResponseDto(any(Member.class))).willReturn(response);

		String content = gson.toJson(post);
		// when
		ResultActions postActions =
			mockMvc.perform(
				post("/api/members")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
					.header("Authorization", "Required JWT access token")
			);
		// then
		postActions
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.memberId").value(1))
			.andDo(getDefaultDocument(
					"post-member",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					MemberFieldDescriptor.getPostRequestPartFieldsSnippet(),
					MemberFieldDescriptor.getSingleResponseSnippet()
				)
			);

	}

	@Test
	void patchMemberTest() throws Exception {
		// given
		patch.setMemberId(1L);
		given(memberMapper.memberPatchDtoToMember(any(MemberDto.Patch.class)))
			.willReturn(member);
		given(memberService.patchMember(any(Member.class))).willReturn(member);
		given(memberMapper.memberToMemberResponseDto(any(Member.class))).willReturn(response);

		String content = gson.toJson(patch);
		// when
		ResultActions patchActions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.patch("/api/members/{memberId}", 1L)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
					.header("Authorization", "Required JWT access token")
			);
		// then
		patchActions
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.memberId").value(1))
			.andDo(getDefaultDocument(
					"patch-member",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					pathParameters(List.of(parameterWithName("memberId")
						.description("The id of the member to update"))),
					MemberFieldDescriptor.getPatchRequestFieldsSnippet(),
					MemberFieldDescriptor.getSingleResponseSnippet()
				)
			);
	}

	@Test
	void getMemberByIdTest() throws Exception {
		// given
		given(memberService.getMemberById(Mockito.anyLong())).willReturn(member);
		given(memberMapper.memberToMemberResponseDto(any(Member.class))).willReturn(response);

		String content = gson.toJson(patch);
		// when
		ResultActions getActions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/members/{memberId}", 1L)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
					.header("Authorization", "Required JWT access token")
			);
		// then
		getActions
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.memberId").value(1))
			.andDo(getDefaultDocument(
					"get-member-by-id",
					pathParameters(List.of(parameterWithName("memberId")
						.description("The id of the member to update"))),
					MemberFieldDescriptor.getSingleResponseSnippet()
				)
			);
	}

	@Test
	void getAllMemberTest() throws Exception {
		// given
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", "1");
		queryParams.add("size", "10");
		given(memberService.getMembers(anyInt(), anyInt())).willReturn(memberPage);
		given(memberMapper.membersToMemberResponseDtos(Mockito.anyList())).willReturn(List.of(response));

		// when
		ResultActions getActions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/members")
					.queryParams(queryParams)
					.accept(MediaType.APPLICATION_JSON)
			);
		// then
		getActions
			.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument(
					"get-all-member",
					requestParameters(List.of(
						parameterWithName("page").description("Page 번호"),
						parameterWithName("size").description("Page에 표시할 회원 수"))),
					MemberFieldDescriptor.getMultiResponseSnippet()
				)
			);

	}

	@Test
	void deleteMember() throws Exception {
		// given
		response.setMemberStatus(Member.MemberStatus.DELETED);
		given(memberService.deleteMember(Mockito.anyLong())).willReturn(member);
		given(memberMapper.memberToMemberResponseDto(any(Member.class))).willReturn(response);

		// when
		ResultActions deleteActions = mockMvc.perform(
			RestDocumentationRequestBuilders.delete("/api/members/{member-id}", 1L)
				.header("Authorization", "Required JWT access token"));

		// then
		deleteActions.andDo(print())
			.andExpect(status().isAccepted())
			.andExpect(jsonPath("$.data.memberId").value(1))
			.andExpect(jsonPath("$.data.memberStatus").value("DELETED"))
			.andDo(getDefaultDocument("delete-member",
				requestHeaders(headerWithName("Authorization").description("JWT Access Token")),
				pathParameters(List.of(
					parameterWithName("member-id").description("member Id that will be deleted"))),
				MemberFieldDescriptor.getSingleResponseSnippet()
			));
	}
}
