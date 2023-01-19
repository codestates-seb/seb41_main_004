package com.codestates.azitserver.domain.club.controller.descriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.snippet.Snippet;

public class ClubReportFieldDescriptor {
	public static RequestFieldsSnippet getPostRequestFieldsSnippet() {
		return requestFields(
			fieldWithPath("reportCategory").type(JsonFieldType.STRING).description("아지트 신고 카테고리")
				.attributes(key("constraints").value("들어갈 항목은 개별적으로 공유")),
			fieldWithPath("reportReason").type(JsonFieldType.STRING).description("신고 구체적인 사유").optional()
		);
	}

	public static Snippet getSingleResponseFieldsSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터")
		).andWithPrefix("data.",
			fieldWithPath("clubReportId").type(JsonFieldType.NUMBER).description("아지트 고유 식별자"),
			fieldWithPath("reportCategory").type(JsonFieldType.STRING).description("아지트 신고 카테고리"),
			fieldWithPath("reportReason").type(JsonFieldType.STRING).description("신고 구체적인 사유"),
			fieldWithPath("club").type(JsonFieldType.OBJECT).description("아지트 정보")
		).andWithPrefix("data.club.",
			fieldWithPath("clubId").type(JsonFieldType.NUMBER).description("아지트 고유 식별자"),
			fieldWithPath("clubName").type(JsonFieldType.STRING).description("아지트 이름"),
			fieldWithPath("clubInfo").type(JsonFieldType.STRING).description("아지트 소개"),
			fieldWithPath("clubStatus").type(JsonFieldType.STRING).description("아지트 활성화 상태"),
			fieldWithPath("host").type(JsonFieldType.OBJECT).description("아지트 호스트 정보")
		).andWithPrefix("data.club.host.",
			fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
			fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
			fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 이름"),
			fieldWithPath("fileInfo").type(JsonFieldType.OBJECT).description("회원 프로필 이미지")
		).andWithPrefix("data.club.host.fileInfo.",
			fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("파일 고유 식별자"),
			fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("파일 저장 경로"),
			fieldWithPath("fileName").type(JsonFieldType.STRING).description("파일 이름")
		).and(
			fieldWithPath("data.club.categorySmall").type(JsonFieldType.OBJECT).description("아지트 소분류 카테고리")
		).andWithPrefix("data.club.categorySmall.",
			fieldWithPath("categorySmallId").type(JsonFieldType.NUMBER).description("아지트 소분류 카테고리 고유 식별자"),
			fieldWithPath("categoryName").type(JsonFieldType.STRING).description("아지트 소분류 카테고리 이름"),
			fieldWithPath("categoryLargeId").type(JsonFieldType.NUMBER)
				.description("아지트 대분류 카테고리 고유 식별자")
				.optional()
		).and(
			fieldWithPath("data.member").type(JsonFieldType.OBJECT).description("아지트 배너 이미지")
		).andWithPrefix("data.member.",
			fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
			fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
			fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
			fieldWithPath("fileInfo").type(JsonFieldType.OBJECT).description("회원 프로필 이미지")
		).andWithPrefix("data.member.fileInfo.",
			fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("파일 고유 식별자"),
			fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("파일 저장 경로"),
			fieldWithPath("fileName").type(JsonFieldType.STRING).description("파일 이름")
		);
	}
}

