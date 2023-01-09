package com.codestates.azitserver.global.exception.controller;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.azitserver.config.CustomExceptionCodeSnippet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = ExceptionRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class ExceptionRestControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void getAll() throws Exception {
		// given
		// when
		ResultActions actions = mockMvc.perform(get("/api/errors"));

		// then
		MvcResult result = actions.andReturn();
		Map<Object, String> errors = objectMapper.readValue(
			result.getResponse().getContentAsByteArray(), new TypeReference<>() {
			});

		actions
			.andDo(print())
			.andDo(
				getDefaultDocument(
					"exception-code",
					new CustomExceptionCodeSnippet(
						"exception-code",
						getDescriptors(errors),
						attributes(key("title").value("ExceptionCode")),
						true
					)
				)
			);
	}

	private List<FieldDescriptor> getDescriptors(Map<Object, String> errors) {
		return errors.entrySet().stream()
			.map(error -> fieldWithPath(error.getKey().toString()).description(error.getValue()))
			.collect(Collectors.toList());
	}
}