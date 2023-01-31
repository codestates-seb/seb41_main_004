package com.codestates.azitserver.domain.member.controller.descriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.snippet.Snippet;

public class MemberReportFieldDescriptor {
	public static RequestFieldsSnippet getPostRequestFieldsSnippet() {
		return requestFields(
			fieldWithPath("reporterId").type(JsonFieldType.NUMBER).description("신고자 고유 식별자"),
			fieldWithPath("reporteeId").type(JsonFieldType.NUMBER).description("피신고자 고유 식별자"),
			fieldWithPath("reportCategory").type(JsonFieldType.STRING).description("신고 카테고리"),
			fieldWithPath("reportReason").type(JsonFieldType.STRING).description("신고 사유").optional()
		);
	}

	public static Snippet getSingleResponseSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터")
		).andWithPrefix("data.",
			fieldWithPath("reportId").type(JsonFieldType.NUMBER).description("신고 고유 식별자"),
			fieldWithPath("reporterId").type(JsonFieldType.NUMBER).description("신고자 고유 식별자"),
			fieldWithPath("reporteeId").type(JsonFieldType.NUMBER).description("피신고자 고유 식별자"),
			fieldWithPath("reportCategory").type(JsonFieldType.STRING).description("신고 카테고리"),
			fieldWithPath("reportReason").type(JsonFieldType.STRING).description("신고 사유")
		);
	}

}
