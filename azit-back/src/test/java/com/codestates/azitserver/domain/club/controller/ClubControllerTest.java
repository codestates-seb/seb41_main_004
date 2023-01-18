package com.codestates.azitserver.domain.club.controller;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.codestates.azitserver.domain.club.controller.descriptor.ClubFieldDescriptor;
import com.codestates.azitserver.domain.club.controller.helper.ClubControllerTestHelper;
import com.codestates.azitserver.domain.club.dto.ClubDto;
import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.mapper.ClubMapper;
import com.codestates.azitserver.domain.club.service.ClubService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.stub.ClubStubData;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = ClubController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ClubControllerTest implements ClubControllerTestHelper {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	ClubService clubService;

	@MockBean
	ClubMapper mapper;

	Club club;
	ClubDto.Post post;
	ClubDto.Patch patch;
	ClubDto.Response response;
	MockMultipartFile image;
	Page<Club> clubPage;

	@BeforeEach
	void beforeEach() {
		// Make stub data
		club = ClubStubData.getDefaultClub();
		post = ClubStubData.getClubDtoPost();
		patch = ClubStubData.getClubDtoPatch();
		response = ClubStubData.getClubDtoResponse();
		image = new MockMultipartFile(
			"image",
			"hello-world.png",
			MediaType.MULTIPART_FORM_DATA_VALUE,
			"".getBytes()
		);
		clubPage = ClubStubData.getClubPage();
	}

	@Test
	void postClub() throws Exception {
		// given
		String content = objectMapper.writeValueAsString(post);
		MockMultipartFile data = ClubStubData.getMultipartJsonData(content);

		given(mapper.clubDtoPostToClubEntity(Mockito.any(ClubDto.Post.class))).willReturn(club);
		given(clubService.createClub(Mockito.any(Club.class), any())).willReturn(club);
		given(mapper.clubToClubDtoResponse(Mockito.any(Club.class))).willReturn(response);

		// when
		ResultActions actions = mockMvc.perform(postMultipartRequestBuilder(getClubUrl(), image, data)
			.header("Authorization", "Required JWT access token").characterEncoding(StandardCharsets.UTF_8));

		// then
		actions.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.clubId").value(1))
			.andDo(getDefaultDocument(
					"post-club",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					requestParts(List.of(
						partWithName("data").description("data"),
						partWithName("image").description("image").optional())),
					ClubFieldDescriptor.getPostRequestPartFieldsSnippet(),
					ClubFieldDescriptor.getSingleResponseSnippet()
				)
			);
	}

	@Test
	void postClubImage() throws Exception {
		// given
		long clubId = 1L;

		doNothing().when(clubService).verifyMemberIsClubHost(Mockito.any(Member.class), Mockito.anyLong());
		given(clubService.updateClubImage(Mockito.anyLong(), Mockito.any())).willReturn(club);
		given(mapper.clubToClubDtoResponse(Mockito.any(Club.class))).willReturn(response);

		// when
		ResultActions actions = mockMvc.perform(postMultipartRequestBuilder(getClubUri(), clubId, image)
			.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.clubId").value(1))
			.andDo(getDefaultDocument(
					"patch-club-image",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					pathParameters(List.of(
						parameterWithName("club-id").description("아지트 고유 식별자"))),
					requestParts(List.of(partWithName("image").description("image"))),
					ClubFieldDescriptor.getSingleResponseSnippet()
				)
			);
	}

	@Test
	void patchClub() throws Exception {
		// given
		long clubId = 1L;
		patch.setClubId(clubId);
		String content = objectMapper.writeValueAsString(patch);

		doNothing().when(clubService).verifyMemberIsClubHost(Mockito.any(Member.class), Mockito.anyLong());
		given(mapper.clubDtoPatchToClubEntity(Mockito.any(ClubDto.Patch.class))).willReturn(club);
		given(clubService.updateClub(Mockito.any(Club.class))).willReturn(club);
		given(mapper.clubToClubDtoResponse(Mockito.any(Club.class))).willReturn(response);

		// when
		ResultActions actions = mockMvc.perform(patchRequestBuilder(getClubUri(), content, clubId)
			.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.clubId").value(1))
			.andDo(getDefaultDocument(
					"patch-club-data",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					pathParameters(List.of(
						parameterWithName("club-id").description("아지트 고유 식별자"))),
					ClubFieldDescriptor.getPatchRequestFieldsSnippet(),
					ClubFieldDescriptor.getSingleResponseSnippet()
				)
			);
	}

	@Test
	void deleteClub() throws Exception {
		// given
		long clubId = 1L;
		response.setClubStatus(Club.ClubStatus.CLUB_CANCEL);

		doNothing().when(clubService).verifyMemberIsClubHost(Mockito.any(Member.class), Mockito.anyLong());
		given(clubService.cancelClub(Mockito.anyLong())).willReturn(club);
		given(mapper.clubToClubDtoResponse(any(Club.class))).willReturn(response);

		// when
		ResultActions actions = mockMvc.perform(deleteRequestBuilder(getClubUri(), clubId)
			.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isAccepted())
			.andExpect(jsonPath("$.data.clubId").value(1))
			.andExpect(jsonPath("$.data.clubStatus").value("CLUB_CANCEL"))
			.andDo(getDefaultDocument(
					"delete-club",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					pathParameters(List.of(
						parameterWithName("club-id").description("아지트 고유 식별자"))),
					ClubFieldDescriptor.getSingleResponseSnippet()
				)
			);
	}

	@Test
	void getAllClub() throws Exception {
		// given
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", "1");
		queryParams.add("size", "10");

		given(clubService.findClubs(anyInt(), anyInt())).willReturn(clubPage);
		given(mapper.clubToClubDtoResponse(Mockito.anyList())).willReturn(List.of(response));

		// when
		ResultActions actions = mockMvc.perform(getRequestBuilder(getClubUrl(), queryParams));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument(
					"get-all-clubs",
					requestParameters(
						List.of(
							parameterWithName("page").description("Page 번호"),
							parameterWithName("size").description("Page 크기")
						)
					),
					ClubFieldDescriptor.getMultiResponseSnippet()
				)
			);
	}

	@Test
	void getClubByClubId() throws Exception {
		// given
		long clubId = 1L;
		given(clubService.findClubById(Mockito.anyLong())).willReturn(club);
		given(mapper.clubToClubDtoResponse(Mockito.any(Club.class))).willReturn(response);

		// when
		ResultActions actions = mockMvc.perform(getRequestBuilder(getClubUri(), clubId));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument(
					"get-club-by-club-id",
					pathParameters(List.of(
						parameterWithName("club-id").description("아지트 고유 식별자"))),
					ClubFieldDescriptor.getSingleResponseSnippet()
				)
			);
	}

	@Test
	void getClubByCategoryId() throws Exception {
		// given
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", "1");
		queryParams.add("size", "10");
		queryParams.add("cl", "1");

		given(clubService.findClubsByCategoryLargeId(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
			.willReturn(clubPage);
		given(mapper.clubToClubDtoResponse(Mockito.anyList())).willReturn(List.of(response));

		// when
		ResultActions actions = mockMvc.perform(getRequestBuilder(getClubUrl() + "/category", queryParams));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument(
					"get-club-by-category-id",
					requestParameters(
						List.of(
							parameterWithName("page").description("Page 번호"),
							parameterWithName("size").description("Page 크기"),
							parameterWithName("cl").description("대분류 카테고리 고유 식별자")
						)
					),
					ClubFieldDescriptor.getMultiResponseSnippet()
				)
			);
	}

	@Test
	void getClubByDate() throws Exception {
		// given
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", "1");
		queryParams.add("size", "10");
		queryParams.add("days", "1");

		given(clubService.findClubsByClubMeetingDate(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
			.willReturn(clubPage);
		given(mapper.clubToClubDtoResponse(Mockito.anyList())).willReturn(List.of(response));

		// when
		ResultActions actions = mockMvc.perform(getRequestBuilder(getClubUrl() + "/date", queryParams));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument(
					"get-club-by-meeting-date",
					requestParameters(
						List.of(
							parameterWithName("page").description("Page 번호"),
							parameterWithName("size").description("Page 크기"),
							parameterWithName("days").description("오늘(0) 기준으로 며칠 뒤의 날짜")
								.attributes(key("constraints").value("0 ~ 5 사이의 정수"))
						)
					),
					ClubFieldDescriptor.getMultiResponseSnippet()
				)
			);
	}

	@Test
	void getClubByMemberRecommend() {

		// TODO : implement

	}

	@Test
	void getClubByMemberId() {

		// TODO : implement

	}

	@Test
	void search() throws Exception {
		// given
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", "1");
		queryParams.add("size", "10");
		queryParams.add("keyword", "재밌는");

		given(clubService.findClubsByKeywords(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
			.willReturn(clubPage);
		given(mapper.clubToClubDtoResponse(Mockito.anyList())).willReturn(List.of(response));

		// when
		ResultActions actions = mockMvc.perform(getRequestBuilder(getClubUrl() + "/search", queryParams));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument(
					"search-club",
					requestParameters(
						List.of(
							parameterWithName("page").description("Page 번호"),
							parameterWithName("size").description("Page 크기"),
							parameterWithName("keyword").description("검색어")
								.attributes(key("constraints").value("빈 문자이면 전체 아지트를 리턴합니다."))
						)
					),
					ClubFieldDescriptor.getMultiResponseSnippet()
				)
			);
	}

	@Test
	void testGetClubByMemberRecommend() throws Exception {
		// given
		Long memberId = 1L;
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", "1");
		queryParams.add("size", "10");

		given(clubService.verifyOrFindAll(Mockito.any(Member.class), Mockito.anyLong())).willReturn(false);
		given(clubService.findClubsMemberRecommend(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())).willReturn(
			clubPage);
		given(mapper.clubToClubDtoResponse(Mockito.anyList())).willReturn(List.of(response));

		// when
		ResultActions actions = mockMvc.perform(
			getRequestBuilder(getClubUrl() + "/recommend/{member-id}", queryParams, memberId)
				.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument("get-club-by-member-recommend",
				pathParameters(List.of(
					parameterWithName("member-id").description("회원 고유 식별자"))
				),
				requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
				requestParameters(
					List.of(
						parameterWithName("page").description("Page 번호"),
						parameterWithName("size").description("Page 크기")
					)
				),
				ClubFieldDescriptor.getMultiResponseSnippet()
			));
	}
}
