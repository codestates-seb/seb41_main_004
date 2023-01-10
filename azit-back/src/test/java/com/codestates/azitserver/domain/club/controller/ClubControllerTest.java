package com.codestates.azitserver.domain.club.controller;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.azitserver.domain.club.dto.ClubDto;
import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.mapper.ClubMapper;
import com.codestates.azitserver.domain.club.service.ClubService;
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
	ClubDto.Response response;

	@BeforeEach
	void beforeEach() {
		// Make stub data
		club = ClubStubData.getDefaultClub();
		post = ClubStubData.getClubDtoPost();
		response = ClubStubData.getClubDtoResponse();
	}

	@Test
	void postClub() throws Exception {
		// given
		MockMultipartFile image = new MockMultipartFile(
			"image",
			"hello-world.png",
			MediaType.MULTIPART_FORM_DATA_VALUE,
			"".getBytes()
		);

		String content = objectMapper.writeValueAsString(post);
		MockMultipartFile data = new MockMultipartFile(
			"data",
			"",
			MediaType.APPLICATION_JSON_VALUE,
			content.getBytes()
		);

		given(mapper.clubDtoPostToClubEntity(Mockito.any(ClubDto.Post.class))).willReturn(club);
		given(clubService.createClub(Mockito.any(Club.class), any())).willReturn(club);
		given(mapper.clubToClubDtoResponse(Mockito.any(Club.class))).willReturn(response);

		// when
		ResultActions actions = mockMvc.perform(postMultipartRequestBuilder(getClubUrl(), image, data)
			.header("Authorization", "Required JWT access token"));

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
					requestPartFields("data",
						fieldWithPath("clubName").type(JsonFieldType.STRING).description("아지트 이름"),
						fieldWithPath("clubInfo").type(JsonFieldType.STRING).description("아지트 소개"),
						fieldWithPath("memberLimit").type(JsonFieldType.NUMBER).description("참가 인원 제한"),
						fieldWithPath("fee").type(JsonFieldType.NUMBER).description("참가비"),
						fieldWithPath("joinMethod").type(JsonFieldType.STRING).description("참가 방식 [ APPROVAL | FIRST_COME ]"),
						fieldWithPath("birthYearMin").type(JsonFieldType.STRING).description("최소 년생 제한"),
						fieldWithPath("birthYearMax").type(JsonFieldType.STRING).description("최대 년생 제한"),
						fieldWithPath("genderRestriction").type(JsonFieldType.STRING).description("성별 제한 [ MALE_ONLY | FEMALE_ONLY | ALL ]"),
						fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("날짜 및 시간"),
						fieldWithPath("isOnline").type(JsonFieldType.BOOLEAN).description("만남 타입 [ true : 온라인 | false : 오프라인 ]"),
						fieldWithPath("location").type(JsonFieldType.STRING).description("오프라인 만남 장소"),
						fieldWithPath("joinQuestion").type(JsonFieldType.STRING).description("참가 신청 질문")
						),
					getSingleResponseSnippet()
				)
			);

	}

	public static ResponseFieldsSnippet getSingleResponseSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터")
		).andWithPrefix("data.",
			fieldWithPath("clubId").type(JsonFieldType.NUMBER).description("아지트 고유 식별자"),
			fieldWithPath("clubName").type(JsonFieldType.STRING).description("아지트 이름"),
			fieldWithPath("clubInfo").type(JsonFieldType.STRING).description("아지트 소개"),
			fieldWithPath("memberLimit").type(JsonFieldType.NUMBER).description("참가 인원 제한"),
			fieldWithPath("fee").type(JsonFieldType.NUMBER).description("참가비"),
			fieldWithPath("joinMethod").type(JsonFieldType.STRING).description("참가 방식 [ APPROVAL | FIRST_COME ]"),
			fieldWithPath("birthYearMin").type(JsonFieldType.STRING).description("최소 년생 제한"),
			fieldWithPath("birthYearMax").type(JsonFieldType.STRING).description("최대 년생 제한"),
			fieldWithPath("genderRestriction").type(JsonFieldType.STRING).description("성별 제한 [ MALE_ONLY | FEMALE_ONLY | ALL ]"),
			fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("날짜 및 시간"),
			fieldWithPath("isOnline").type(JsonFieldType.BOOLEAN).description("만남 타입 [ true : 온라인 | false : 오프라인 ]"),
			fieldWithPath("location").type(JsonFieldType.STRING).description("오프라인 만남 장소"),
			fieldWithPath("joinQuestion").type(JsonFieldType.STRING).description("참가 신청 질문"),
			fieldWithPath("clubStatus").type(JsonFieldType.STRING).description("아지트 활성화 상태 [ CLUB_ACTIVE | CLUB_CANCEL ]")
		);
	}
}
