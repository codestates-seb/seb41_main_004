// package com.codestates.azitserver.domain.member.controller;
//
// import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.BDDMockito.*;
// import static org.springframework.restdocs.headers.HeaderDocumentation.*;
// import static org.springframework.restdocs.request.RequestDocumentation.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import java.nio.charset.StandardCharsets;
// import java.util.List;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
// import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
// import org.springframework.http.MediaType;
// import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;
//
// import com.codestates.azitserver.domain.member.controller.descriptor.MemberReportFieldDescriptor;
// import com.codestates.azitserver.domain.member.dto.MemberReportDto;
// import com.codestates.azitserver.domain.member.entity.Member;
// import com.codestates.azitserver.domain.member.entity.MemberReport;
// import com.codestates.azitserver.domain.member.mapper.MemberReportMapper;
// import com.codestates.azitserver.domain.member.service.MemberReportService;
// import com.codestates.azitserver.domain.member.service.MemberService;
// import com.codestates.azitserver.domain.stub.MemberReportStubData;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.google.gson.Gson;
//
// @AutoConfigureMockMvc(addFilters = false)
// @WebMvcTest(controllers = MemberReportController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
// @MockBean(JpaMetamodelMappingContext.class)
// @AutoConfigureRestDocs
// public class MemberReportControllerTest {
//
// 	@Autowired
// 	private MockMvc mockMvc;
//
// 	@Autowired
// 	private ObjectMapper objectMapper;
//
// 	@MockBean
// 	private MemberService memberService;
//
// 	@MockBean
// 	private MemberReportService memberReportService;
//
// 	@MockBean
// 	private MemberReportMapper memberReportMapper;
//
// 	@Autowired
// 	private Gson gson;
//
// 	MemberReport memberReport;
// 	Member reporter;
// 	Member reportee;
//
// 	MemberReportDto.Post post;
// 	MemberReportDto.Response response;
//
// 	@BeforeEach
// 	void beforeEach() {
// 		reporter = MemberReportStubData.reporter();
// 		reportee = MemberReportStubData.reportee();
// 		post = MemberReportStubData.stubMemberReportDtoPost();
// 		response = MemberReportStubData.stubMemberReportDtoResponse();
//
// 	}
//
// 	@Test
// 	void postMemberReportTest() throws Exception {
// 		// given
// 		given(memberReportMapper.reportPostDtoToReport(any(MemberReportDto.Post.class)))
// 			.willReturn(memberReport);
// 		given(memberReportService.createMemberReport(any(MemberReport.class))).willReturn(memberReport);
// 		given(memberReportMapper.reportToReportResponseDto(any(MemberReport.class))).willReturn(response);
//
// 		String content = objectMapper.writeValueAsString(post);
//
// 		// when
// 		ResultActions postActions =
// 			mockMvc.perform(
// 				RestDocumentationRequestBuilders.post("/api/members/reports")
// 					.accept(MediaType.APPLICATION_JSON)
// 					.contentType(MediaType.APPLICATION_JSON)
// 					.header("Authorization", "Required JWT access token")
// 					.content(content)
// 					.characterEncoding(StandardCharsets.UTF_8)
// 			);
// 		// then
// 		postActions
// 			.andDo(print())
// 			.andExpect(status().isCreated())
// 			.andDo(getDefaultDocument(
// 					"post-member-report",
// 					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
// 					MemberReportFieldDescriptor.getPostRequestFieldsSnippet()	,
// 					MemberReportFieldDescriptor.getSingleResponseSnippet()
// 				)
// 			);
//
// 	}
//
//
// }
//
