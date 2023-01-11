package com.codestates.azitserver.domain.category.controller;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.azitserver.domain.category.dto.CategoryDto;
import com.codestates.azitserver.domain.category.mapper.CategoryMapper;
import com.codestates.azitserver.domain.category.service.CategoryService;
import com.codestates.azitserver.domain.stub.CategoryStubData;

@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = CategoryController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class CategoryControllerTest implements CategoryControllerTestHelper {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	CategoryService categoryService;

	@MockBean
	CategoryMapper mapper;

	CategoryDto.Response response;
	CategoryDto.LargeResponse largeResponse;
	CategoryDto.SmallResponse smallResponse;

	@BeforeEach
	void beforeEach() {
		// Make stub data
		response = CategoryStubData.getResponseData();
		largeResponse = CategoryStubData.getLargeResponse();
		smallResponse = CategoryStubData.getSmallResponse();
	}

	@Test
	void getAllCategory() throws Exception {
		// given
		given(categoryService.getAllLargeCategory()).willReturn(new ArrayList<>());
		given(mapper.categoryLargeToCategoryResponseDto(Mockito.anyList())).willReturn(List.of(response));

		// when
		ResultActions actions = mockMvc.perform(getRequestBuilder(getCategoryUrl())
			.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument(
					"get-all-categories",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					CategoryFieldDescriptor.getResponseSnippet()
				)
			);
	}

	@Test
	void getLargeCategory() throws Exception {
		// given
		given(categoryService.getAllLargeCategory()).willReturn(new ArrayList<>());
		given(mapper.categoryLargeToCategoryLargeResponseDto(Mockito.anyList())).willReturn(List.of(largeResponse));

		// when
		ResultActions actions = mockMvc.perform(getRequestBuilder(getCategoryUrl() + "/large")
			.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument(
					"get-all-large-categories",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					CategoryFieldDescriptor.getLargeResponseSnippet()
				)
			);
	}

	@Test
	void getSmallCategory() throws Exception {
		// given
		given(categoryService.getAllSmallCategory()).willReturn(new ArrayList<>());
		given(mapper.categoryLargeToCategorySmallResponseDto(Mockito.anyList())).willReturn(List.of(smallResponse));

		// when
		ResultActions actions = mockMvc.perform(getRequestBuilder(getCategoryUrl() + "/small")
			.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument(
					"get-all-small-categories",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					CategoryFieldDescriptor.getSmallResponseSnippet()
				)
			);
	}
}