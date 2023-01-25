package com.codestates.azitserver.global.utils;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

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
	default MockHttpServletRequestBuilder postMultipartRequestBuilder(String url, Long resourceId,
		MockMultipartFile file) {
		return multipart(url, resourceId)
			.file(file)
			.accept(MediaType.APPLICATION_JSON);
	}

	default MockHttpServletRequestBuilder postRequestBuilder(URI uri, String content) {
		return post(uri)
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding(StandardCharsets.UTF_8)
			.content(content);
	}

	default MockHttpServletRequestBuilder getRequestBuilder(String url) {
		return get(url)
			.accept(MediaType.APPLICATION_JSON);
	}

	default MockHttpServletRequestBuilder getRequestBuilder(String url, MultiValueMap<String, String> queryParams, Object... urlVariables) {
		return get(url, urlVariables)
			.queryParams(queryParams)
			.accept(MediaType.APPLICATION_JSON);
	}

	default MockHttpServletRequestBuilder getRequestBuilder(String url, MultiValueMap<String, String> queryParams, String content, Object... urlVariables) {
		return get(url, urlVariables)
			.queryParams(queryParams)
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding(StandardCharsets.UTF_8)
			.content(content);
	}

	default MockHttpServletRequestBuilder getRequestBuilder(String url, long resourceId) {
		return get(url, resourceId)
			.accept(MediaType.APPLICATION_JSON);
	}

	default MockHttpServletRequestBuilder patchRequestBuilder(String urlTemplate, String content,
		Object... urlVariables) {
		return patch(urlTemplate, urlVariables)
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding(StandardCharsets.UTF_8)
			.content(content);
	}

	default MockHttpServletRequestBuilder deleteRequestBuilder(String url, long resourceId) {
		return delete(url, resourceId);
	}

	default URI createURI(String url) {
		return UriComponentsBuilder.newInstance().path(url).build().toUri();
	}

	/**
	 * uri 생성 메서드
	 * @param url 요청을 보낼 url
	 * @param resources url의 쿼리 파라미터, 파라미터 값을 key, value로 받는 Map
	 * @return 완성된 uri
	 * @author cryoon
	 */
	default URI createURI(String url, Map<String, ?> resources) {
		return UriComponentsBuilder.newInstance().path(url).buildAndExpand(resources).toUri();
	}
}
