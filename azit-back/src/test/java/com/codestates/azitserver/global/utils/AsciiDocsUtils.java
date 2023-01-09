package com.codestates.azitserver.global.utils;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.snippet.Snippet;

public class AsciiDocsUtils {
	public static OperationRequestPreprocessor getRequestPreProcessor() {
		return preprocessRequest(
			modifyUris()  // api docs 상에 도메인 주소를 바꾸기 위해 사용합니다.
				.scheme("https")
				.host("azit-docs.api.com")
				.removePort(),
			prettyPrint());
	}

	public static OperationResponsePreprocessor getResponsePreProcessor() {
		return preprocessResponse(prettyPrint());
	}

	public static RestDocumentationResultHandler getDefaultDocument(String identifier, Snippet... snippets) {
		return document(
			identifier,
			getRequestPreProcessor(),
			getResponsePreProcessor(),
			snippets
		);
	}
}