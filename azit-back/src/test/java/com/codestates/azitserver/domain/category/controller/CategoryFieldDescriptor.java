package com.codestates.azitserver.domain.category.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.snippet.Snippet;

public class CategoryFieldDescriptor {
	public static ResponseFieldsSnippet getResponseSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터")
		).andWithPrefix("data[].",
			fieldWithPath("categoryLargeId").type(JsonFieldType.NUMBER).description("대분류 카테고리 고유 식별자"),
			fieldWithPath("categoryName").type(JsonFieldType.STRING).description("대분류 카테고리 이름"),
			fieldWithPath("categorySmall").type(JsonFieldType.ARRAY).description("소분류 카테고리 데이터")
		).andWithPrefix("data[].categorySmall[].",
			fieldWithPath("categorySmallId").type(JsonFieldType.NUMBER).description("소분류 카테고리 고유 식별자"),
			fieldWithPath("categoryName").type(JsonFieldType.STRING).description("소분류 카테고리 이름"),
			fieldWithPath("categoryLargeId").type(JsonFieldType.NUMBER).description("대분류 카테고리 고유 식별자")
		);
	}

	public static Snippet getLargeResponseSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터")
		).andWithPrefix("data[].",
			fieldWithPath("categoryLargeId").type(JsonFieldType.NUMBER).description("대분류 카테고리 고유 식별자"),
			fieldWithPath("categoryName").type(JsonFieldType.STRING).description("대분류 카테고리 이름")
		);
	}

	public static Snippet getSmallResponseSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터")
		).andWithPrefix("data[].",
			fieldWithPath("categorySmallId").type(JsonFieldType.NUMBER).description("소분류 카테고리 고유 식별자"),
			fieldWithPath("categoryName").type(JsonFieldType.STRING).description("소분류 카테고리 이름"),
			fieldWithPath("categoryLargeId").type(JsonFieldType.NUMBER).description("대분류 카테고리 고유 식별자")
		);
	}

}
