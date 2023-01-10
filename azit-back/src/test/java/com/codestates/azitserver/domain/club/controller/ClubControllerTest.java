package com.codestates.azitserver.domain.club.controller;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
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
	ClubDto.Patch patch;
	ClubDto.Response response;
	MockMultipartFile image;

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
					ClubFieldDescriptor.getPostRequestPartFieldsSnippet(),
					ClubFieldDescriptor.getSingleResponseSnippet()
				)
			);
	}

	@Test
	void patchClub() throws Exception {
		// given
		Long clubId = 1L;
		patch.setClubId(clubId);
		String content = objectMapper.writeValueAsString(patch);
		MockMultipartFile data = ClubStubData.getMultipartJsonData(content);

		given(mapper.clubDtoPatchToClubEntity(Mockito.any(ClubDto.Patch.class))).willReturn(club);
		given(clubService.updateClub(Mockito.any(Club.class), any())).willReturn(club);
		given(mapper.clubToClubDtoResponse(Mockito.any(Club.class))).willReturn(response);

		// when
		ResultActions actions = mockMvc.perform(patchMultipartRequestBuilder(getClubUri(), clubId, image, data)
			.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.clubId").value(1))
			.andDo(getDefaultDocument(
					"patch-club",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					pathParameters(List.of(
						parameterWithName("club-id").description("아지트 고유 식별자"))),
					requestParts(List.of(
						partWithName("data").description("data"),
						partWithName("image").description("image").optional())),
					ClubFieldDescriptor.getPatchRequestPartFieldsSnippet(),
					ClubFieldDescriptor.getSingleResponseSnippet()
				)
			);
	}

	@Test
	void deleteClub() throws Exception {
		// given
		long clubId = 1L;
		response.setClubStatus(Club.ClubStatus.CLUB_CANCEL);
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
}
