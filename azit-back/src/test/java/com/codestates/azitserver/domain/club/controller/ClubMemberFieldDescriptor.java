package com.codestates.azitserver.domain.club.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.*;

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
			fieldWithPath("avatarImageId").type(JsonFieldType.NUMBER).description("프로필 사진 파일 고유 식별자"),
			fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
			fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
			fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").ignored(),
			fieldWithPath("gender").type(JsonFieldType.STRING).description("성별")
				.attributes(key("constraints").value("MALE | FEMALE")),
			fieldWithPath("birthYear").type(JsonFieldType.STRING).description("출생년도"),
			fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기소개").optional(),
			fieldWithPath("reputation").type(JsonFieldType.NUMBER).description("평판 점수")
				.attributes(key("constraints").value("기본값 10")),
			fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태")
				.attributes(key("constraints").value("ACTIVE: 활성 | DELETED: 탈퇴"))
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
			fieldWithPath("avatarImageId").type(JsonFieldType.NUMBER).description("프로필 사진 파일 고유 식별자"),
			fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
			fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
			fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").ignored(),
			fieldWithPath("gender").type(JsonFieldType.STRING).description("성별")
				.attributes(key("constraints").value("MALE | FEMALE")),
			fieldWithPath("birthYear").type(JsonFieldType.STRING).description("출생년도"),
			fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기소개").optional(),
			fieldWithPath("reputation").type(JsonFieldType.NUMBER).description("평판 점수")
				.attributes(key("constraints").value("기본값 10")),
			fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태")
				.attributes(key("constraints").value("ACTIVE: 활성 | DELETED: 탈퇴"))
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
