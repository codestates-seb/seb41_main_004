package com.codestates.azitserver.global.utils;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.MultiValueMap;

public interface ControllerTestHelper {
	// TODO : MockMultipartFile을 가변인자로 받을 수 있는 방법 고민해보기
	default MockHttpServletRequestBuilder postMultipartRequestBuilder(String url, MockMultipartFile file1,
		MockMultipartFile file2) {
		return multipart(url)
			.file(file1)
			.file(file2)
			.accept(MediaType.APPLICATION_JSON);
	}

	// form 형식의 요청은 GET, POST만 가능하다?! https://mangkyu.tistory.com/218
	default MockHttpServletRequestBuilder patchMultipartRequestBuilder(String url, Long resourceId,
		MockMultipartFile file1, MockMultipartFile file2) {
		return multipart(url, resourceId)
			.file(file1)
			.file(file2)
			.accept(MediaType.APPLICATION_JSON)
			.with(req -> {
				req.setMethod(HttpMethod.PATCH.name());
				return req;
			});
	}

	// TODO : 작성 예정
	default MockHttpServletRequestBuilder postRequestBuilder(String url) {
		return null;
	}

	default MockHttpServletRequestBuilder getRequestBuilder(String url) {
		return get(url)
			.accept(MediaType.APPLICATION_JSON);
	}

	default MockHttpServletRequestBuilder getRequestBuilder(String url, MultiValueMap<String, String> queryParams) {
		return get(url)
			.queryParams(queryParams)
			.accept(MediaType.APPLICATION_JSON);
	}

	// TODO : 작성 예정
	default MockHttpServletRequestBuilder getRequestBuilder(String url, long resourceId) {
		return get(url, resourceId)
			.accept(MediaType.APPLICATION_JSON);
	}

	// TODO : 작성 예정
	default MockHttpServletRequestBuilder patchRequestBuilder(String url, long resourceId) {
		return null;
	}

	default MockHttpServletRequestBuilder deleteRequestBuilder(String url, long resourceId) {
		return delete(url, resourceId);
	}
}
