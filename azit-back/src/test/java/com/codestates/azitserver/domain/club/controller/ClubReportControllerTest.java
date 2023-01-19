package com.codestates.azitserver.domain.club.controller;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.azitserver.domain.club.controller.descriptor.ClubReportFieldDescriptor;
import com.codestates.azitserver.domain.club.dto.ClubReportDto;
import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.entity.ClubReport;
import com.codestates.azitserver.domain.club.mapper.ClubReportMapper;
import com.codestates.azitserver.domain.club.service.ClubReportService;
import com.codestates.azitserver.domain.club.service.ClubService;
import com.codestates.azitserver.domain.stub.ClubReportStubData;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = ClubReportController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ClubReportControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	ClubReportService clubReportService;

	@MockBean
	ClubService clubService;

	@MockBean
	ClubReportMapper mapper;

	ClubReportDto.Post post;
	ClubReportDto.Response response;

	@BeforeEach
	void beforeEach() {
		// Make stub data
		post = ClubReportStubData.getClubReportPost();
		response = ClubReportStubData.getClubReportResponse();
	}

	@Test
	void postClubReport() throws Exception {
		// given
		String content = objectMapper.writeValueAsString(post);

		given(mapper.clubReportDtoPostToClubReport(Mockito.any())).willReturn(new ClubReport());
		given(clubService.findClubById(Mockito.anyLong())).willReturn(new Club());
		given(clubReportService.createClubReport(Mockito.any(), Mockito.anyLong(), Mockito.any(ClubReport.class)))
			.willReturn(new ClubReport());
		given(mapper.clubReportToClubReportDtoResponse(Mockito.any(ClubReport.class))).willReturn(response);

		// when
		ResultActions actions = mockMvc.perform(
			post("/api/clubs/reports/{club-id}", 1L)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.header("Authorization", "(Optional) Required JWT access token")
				.content(content));

		// then
		actions.andDo(print())
			.andExpect(status().isCreated())
			.andDo(getDefaultDocument("post-club-report",
				requestHeaders(headerWithName("Authorization").description("(Optional) Jwt Access Token")),
				pathParameters(List.of(
					parameterWithName("club-id").description("아지트 고유 식별자"))),
				ClubReportFieldDescriptor.getPostRequestFieldsSnippet(),
				ClubReportFieldDescriptor.getSingleResponseFieldsSnippet()
			));
	}
}