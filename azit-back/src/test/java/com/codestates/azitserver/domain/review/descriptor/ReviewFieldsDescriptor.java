package com.codestates.azitserver.domain.review.descriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.snippet.Snippet;

public class ReviewFieldsDescriptor {
	public static RequestFieldsSnippet getPostRequestFieldsSnippet() {
		return requestFields(
			fieldWithPath("[]").description("요청 데이터 배열")
		).andWithPrefix("[].",
			fieldWithPath("reviewerId").type(JsonFieldType.NUMBER).description("리뷰 쓰는 사용자 고유 식별자"),
			fieldWithPath("revieweeId").type(JsonFieldType.NUMBER).description("리뷰 대상자 고유 식별자"),
			fieldWithPath("clubId").type(JsonFieldType.NUMBER).description("리뷰어, 리뷰이가 참여하고 있는 아지트 고유 식별자"),
			fieldWithPath("commentCategory").type(JsonFieldType.STRING)
				.description("리뷰 카테고리")
				.attributes(key("constraints").value("enum으로 분류")),
			fieldWithPath("commentBody").type(JsonFieldType.STRING).description("상세 리뷰 내용").optional()
		);
	}

	public static RequestFieldsSnippet getPatchRequestFieldsSnippet() {
		return requestFields(
			fieldWithPath("reviewStatus").type(JsonFieldType.BOOLEAN)
				.description("리뷰 숨김처리 여부")
				.attributes(key("constraints").value("true: 숨김 | false: 공개"))
		);
	}

	public static Snippet getSetRequestFieldsSnippet() {
		return requestFields(
			fieldWithPath("revieweeId").type(JsonFieldType.NUMBER).description("리뷰 대상자 고유 식별자")
		);
	}

	public static ResponseFieldsSnippet getSingleResponseFieldsSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터")
		).andWithPrefix("data.",
			fieldWithPath("reviewId").type(JsonFieldType.NUMBER).description("리뷰 고유 식별자"),
			fieldWithPath("revieweeId").type(JsonFieldType.NUMBER).description("리뷰 대상자 고유 식별자"),
			fieldWithPath("commentCategory").type(JsonFieldType.STRING).description("리뷰 카테고리(enum의 value값)"),
			fieldWithPath("commentBody").type(JsonFieldType.STRING).description("상세 리뷰 내용"),
			fieldWithPath("reviewStatus").type(JsonFieldType.BOOLEAN)
				.description("리뷰 숨김처리 여부")
				.attributes(key("constraints").value("true: 숨김 | false: 공개"))
		).and(
			fieldWithPath("data.club").type(JsonFieldType.OBJECT).description("리뷰어, 리뷰이가 참여하고 있는 아지트 데이터")
		).andWithPrefix("data.club.",
			fieldWithPath("clubId").type(JsonFieldType.NUMBER).description("아지트 고유 식별자"),
			fieldWithPath("clubName").type(JsonFieldType.STRING).description("아지트 이름"),
			fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("약속 날짜")
		);
	}

	public static ResponseFieldsSnippet getSingleArrayResponseFieldsSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터")
		).andWithPrefix("data[].",
			fieldWithPath("reviewId").type(JsonFieldType.NUMBER).description("리뷰 고유 식별자"),
			fieldWithPath("revieweeId").type(JsonFieldType.NUMBER).description("리뷰 대상자 고유 식별자"),
			fieldWithPath("commentCategory").type(JsonFieldType.STRING).description("리뷰 카테고리(enum의 value값)"),
			fieldWithPath("commentBody").type(JsonFieldType.STRING).description("상세 리뷰 내용"),
			fieldWithPath("reviewStatus").type(JsonFieldType.BOOLEAN)
				.description("리뷰 숨김처리 여부")
				.attributes(key("constraints").value("true: 숨김 | false: 공개"))
		).and(
			fieldWithPath("data[].club").type(JsonFieldType.OBJECT).description("리뷰어, 리뷰이가 참여하고 있는 아지트 데이터")
		).andWithPrefix("data[].club.",
			fieldWithPath("clubId").type(JsonFieldType.NUMBER).description("아지트 고유 식별자"),
			fieldWithPath("clubName").type(JsonFieldType.STRING).description("아지트 이름"),
			fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("약속 날짜")
		);
	}

	public static ResponseFieldsSnippet getMultiResponseFieldsSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터")
		).andWithPrefix("data[].",
			fieldWithPath("reviewId").type(JsonFieldType.NUMBER).description("리뷰 고유 식별자"),
			fieldWithPath("revieweeId").type(JsonFieldType.NUMBER).description("리뷰 대상자 고유 식별자"),
			fieldWithPath("commentCategory").type(JsonFieldType.STRING).description("리뷰 카테고리(enum의 value값)"),
			fieldWithPath("commentBody").type(JsonFieldType.STRING).description("상세 리뷰 내용"),
			fieldWithPath("reviewStatus").type(JsonFieldType.BOOLEAN)
				.description("리뷰 숨김처리 여부")
				.attributes(key("constraints").value("true: 숨김 | false: 공개"))
		).and(
			fieldWithPath("data[].club").type(JsonFieldType.OBJECT).description("리뷰어, 리뷰이가 참여하고 있는 아지트 데이터")
		).andWithPrefix("data[].club.",
			fieldWithPath("clubId").type(JsonFieldType.NUMBER).description("아지트 고유 식별자"),
			fieldWithPath("clubName").type(JsonFieldType.STRING).description("아지트 이름"),
			fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("약속 날짜")
		).and(
			fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보")
		).andWithPrefix("pageInfo.",
			fieldWithPath("page").type(JsonFieldType.NUMBER).description("요청한 페이지"),
			fieldWithPath("size").type(JsonFieldType.NUMBER).description("요청한 개수"),
			fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
			fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 개수")
		);
	}
}