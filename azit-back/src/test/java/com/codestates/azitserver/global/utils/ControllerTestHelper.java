package com.codestates.azitserver.global.utils;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public interface ControllerTestHelper {
	default MockHttpServletRequestBuilder postMultipartRequestBuilder(String url, MockMultipartFile file1, MockMultipartFile file2) {
		return multipart(url)
			.file(file1)
			.file(file2)
			.accept(MediaType.APPLICATION_JSON);
	}
}
