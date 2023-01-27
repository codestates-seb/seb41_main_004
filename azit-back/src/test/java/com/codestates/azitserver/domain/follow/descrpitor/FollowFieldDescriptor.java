package com.codestates.azitserver.domain.follow.descrpitor;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

public class FollowFieldDescriptor {
	public static ResponseFieldsSnippet getFollowDtoResponseFieldsSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터")
		).andWithPrefix("data.",
				fieldWithPath("followId").type(JsonFieldType.NUMBER).description("팔로우 정보 고유 식별자")
			).and(
				fieldWithPath("data.follower").type(JsonFieldType.OBJECT).description("팔로우 한 사람 정보")
			)
			.andWithPrefix("data.follower.",
				fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
				fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
				fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 이름"),
				fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기 소개"),
				fieldWithPath("fileInfo").type(JsonFieldType.OBJECT).description("회원 프로필 이미지")
			).andWithPrefix("data.follower.fileInfo.",
				fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("파일 고유 식별자"),
				fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("파일 저장 경로"),
				fieldWithPath("fileName").type(JsonFieldType.STRING).description("파일 이름")
			).and(
				fieldWithPath("data.followee").type(JsonFieldType.OBJECT).description("팔로우 당한 사람 정보")
			)
			.andWithPrefix("data.followee.",
				fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
				fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
				fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 이름"),
				fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기 소개"),
				fieldWithPath("fileInfo").type(JsonFieldType.OBJECT).description("회원 프로필 이미지")
			).andWithPrefix("data.followee.fileInfo.",
				fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("파일 고유 식별자"),
				fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("파일 저장 경로"),
				fieldWithPath("fileName").type(JsonFieldType.STRING).description("파일 이름")
			);
	}

	public static ResponseFieldsSnippet getFollowDtoGetFollowerResponseFieldsSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터")
		).andWithPrefix("data[].",
				fieldWithPath("followId").type(JsonFieldType.NUMBER).description("팔로우 정보 고유 식별자"),
				fieldWithPath("matpal").type(JsonFieldType.BOOLEAN).description("서로 팔로우 여부")
			).and(
				fieldWithPath("data[].follower").type(JsonFieldType.OBJECT).description("팔로우 한 사람 정보")
			)
			.andWithPrefix("data[].follower.",
				fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
				fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
				fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 이름"),
				fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기 소개"),
				fieldWithPath("fileInfo").type(JsonFieldType.OBJECT).description("회원 프로필 이미지")
			).andWithPrefix("data[].follower.fileInfo.",
				fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("파일 고유 식별자"),
				fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("파일 저장 경로"),
				fieldWithPath("fileName").type(JsonFieldType.STRING).description("파일 이름")
			).and(
				fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보")
			).andWithPrefix("pageInfo.",
				fieldWithPath("page").type(JsonFieldType.NUMBER).description("요청한 페이지"),
				fieldWithPath("size").type(JsonFieldType.NUMBER).description("요청한 개수"),
				fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
				fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 개수")
			);
	}

	public static ResponseFieldsSnippet getFollowDtoGetFollowingResponseFieldsSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터")
		).andWithPrefix("data[].",
				fieldWithPath("followId").type(JsonFieldType.NUMBER).description("팔로우 정보 고유 식별자"),
				fieldWithPath("matpal").type(JsonFieldType.BOOLEAN).description("서로 팔로우 여부")
			).and(
				fieldWithPath("data[].followee").type(JsonFieldType.OBJECT).description("팔로우 당한 사람 정보")
			)
			.andWithPrefix("data[].followee.",
				fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
				fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
				fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 이름"),
				fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기 소개"),
				fieldWithPath("fileInfo").type(JsonFieldType.OBJECT).description("회원 프로필 이미지")
			).andWithPrefix("data[].followee.fileInfo.",
				fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("파일 고유 식별자"),
				fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("파일 저장 경로"),
				fieldWithPath("fileName").type(JsonFieldType.STRING).description("파일 이름")
			).and(
				fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보")
			).andWithPrefix("pageInfo.",
				fieldWithPath("page").type(JsonFieldType.NUMBER).description("요청한 페이지"),
				fieldWithPath("size").type(JsonFieldType.NUMBER).description("요청한 개수"),
				fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
				fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 개수")
			);
	}

	public static ResponseFieldsSnippet getFollowStatusFieldsSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터")
		).andWithPrefix("data.",
			fieldWithPath("result").type(JsonFieldType.BOOLEAN).description("서로 팔로우 여부")
		);
	}
}
