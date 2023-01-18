package com.codestates.azitserver.domain.club.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.snippet.Snippet;

public class ClubMemberFieldDescriptor {
	public static RequestFieldsSnippet getSignupRequestFieldsSnippet() {
		return requestFields(
			fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("참여 신청을 보내는 회원 고유 식별자"),
			fieldWithPath("joinAnswer").type(JsonFieldType.STRING).description("참여 신청시 참여 질문에 대한 대답")
		);
	}

	public static RequestFieldsSnippet getPatchRequestFieldsSnippet() {
		return requestFields(
			fieldWithPath("status").type(JsonFieldType.STRING).description("바꿀 참여 상태")
		);
	}

	public static Snippet getSingleResponseFieldSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터")
		).andWithPrefix("data.",
			fieldWithPath("clubMemberId").type(JsonFieldType.NUMBER).description("참여 신청 고유 식별자"),
			fieldWithPath("joinAnswer").type(JsonFieldType.STRING).description("참여 신청시 참여 질문에 대한 대답"),
			fieldWithPath("clubMemberStatus").type(JsonFieldType.STRING).description("현재 참여 신청 상태")
		).and(
			fieldWithPath("data.member").type(JsonFieldType.OBJECT).description("참여 신청한 회원 정보")
		).andWithPrefix("data.member.",
			fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
			fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
			fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
		).and(
			fieldWithPath("data.member.fileInfo").type(JsonFieldType.OBJECT).description("프로필 사진")
		).andWithPrefix("data.member.fileInfo.",
			fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("프로필 사진 고유 식별자"),
			fieldWithPath("fileName").type(JsonFieldType.STRING).description("프로필 사진 파일명"),
			fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("프로핀 사진 파일 경로")
		);
	}

	public static Snippet getMultiResponseFieldSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터")
		).andWithPrefix("data[].",
			fieldWithPath("clubMemberId").type(JsonFieldType.NUMBER).description("참여 신청 고유 식별자"),
			fieldWithPath("joinAnswer").type(JsonFieldType.STRING).description("참여 신청시 참여 질문에 대한 대답"),
			fieldWithPath("clubMemberStatus").type(JsonFieldType.STRING).description("현재 참여 신청 상태")
		).and(
			fieldWithPath("data[].member").type(JsonFieldType.OBJECT).description("참여 신청한 회원 정보")
		).andWithPrefix("data[].member.",
			fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
			fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
			fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
		).and(
			fieldWithPath("data[].member.fileInfo").type(JsonFieldType.OBJECT).description("프로필 사진")
		).andWithPrefix("data[].member.fileInfo.",
			fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("프로필 사진 고유 식별자"),
			fieldWithPath("fileName").type(JsonFieldType.STRING).description("프로필 사진 파일명"),
			fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("프로핀 사진 파일 경로")
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
