package com.codestates.azitserver.domain.club.controller.descriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.RequestPartFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

public class ClubFieldDescriptor {
	public static RequestPartFieldsSnippet getPostRequestPartFieldsSnippet() {
		return requestPartFields("data",
			fieldWithPath("clubName").type(JsonFieldType.STRING).description("아지트 이름"),
			fieldWithPath("clubInfo").type(JsonFieldType.STRING).description("아지트 소개"),
			fieldWithPath("memberLimit").type(JsonFieldType.NUMBER).description("참가 인원 제한"),
			fieldWithPath("fee").type(JsonFieldType.NUMBER).description("참가비"),
			fieldWithPath("joinMethod").type(JsonFieldType.STRING)
				.description("참가 방식").attributes(key("constrains").value("APPROVAL | FIRST_COME")),
			fieldWithPath("birthYearMin").type(JsonFieldType.STRING).description("최소 년생 제한"),
			fieldWithPath("birthYearMax").type(JsonFieldType.STRING).description("최대 년생 제한"),
			fieldWithPath("genderRestriction").type(JsonFieldType.STRING)
				.description("성별 제한").attributes(key("constraints").value("MALE_ONLY | FEMALE_ONLY | ALL")),
			fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("약속 날짜"),
			fieldWithPath("meetingTime").type(JsonFieldType.STRING).description("약속 시간"),
			fieldWithPath("isOnline").type(JsonFieldType.STRING)
				.description("만남 타입").attributes(key("constraints").value("online : 온라인 | offline : 오프라인")),
			fieldWithPath("location").type(JsonFieldType.STRING).description("오프라인 만남 장소")
				.attributes(key("constraints").value("오프라인 만남인 경우에만 입력")).optional(),
			fieldWithPath("categorySmallId").type(JsonFieldType.NUMBER).description("아지트 소분류 카테고리"),
			fieldWithPath("joinQuestion").type(JsonFieldType.STRING).description("참가 신청 질문")
		);
	}

	public static RequestFieldsSnippet getPatchRequestFieldsSnippet() {
		return requestFields(
			fieldWithPath("clubId").type(JsonFieldType.NUMBER).description("아지트 고유 식별자").optional(),
			fieldWithPath("clubName").type(JsonFieldType.STRING).description("아지트 이름").optional(),
			fieldWithPath("clubInfo").type(JsonFieldType.STRING).description("아지트 소개").optional(),
			fieldWithPath("memberLimit").type(JsonFieldType.NUMBER).description("참가 인원 제한").optional(),
			fieldWithPath("fee").type(JsonFieldType.NUMBER).description("참가비").optional(),
			fieldWithPath("birthYearMin").type(JsonFieldType.STRING).description("최소 년생 제한").optional(),
			fieldWithPath("birthYearMax").type(JsonFieldType.STRING).description("최대 년생 제한").optional(),
			fieldWithPath("genderRestriction").type(JsonFieldType.STRING)
				.description("성별 제한").attributes(key("constraints").value("MALE_ONLY | FEMALE_ONLY | ALL")).optional(),
			fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("약속 날짜").optional(),
			fieldWithPath("meetingTime").type(JsonFieldType.STRING).description("약속 시간").optional(),
			fieldWithPath("isOnline").type(JsonFieldType.STRING)
				.description("만남 타입").attributes(key("constraints").value("online : 온라인 | offline : 오프라인")).optional(),
			fieldWithPath("location").type(JsonFieldType.STRING).description("오프라인 만남 장소").optional()
		);
	}

	public static ResponseFieldsSnippet getSingleResponseSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터")
		).andWithPrefix("data.",
				fieldWithPath("clubId").type(JsonFieldType.NUMBER).description("아지트 고유 식별자"),
				fieldWithPath("clubName").type(JsonFieldType.STRING).description("아지트 이름"),
				fieldWithPath("clubInfo").type(JsonFieldType.STRING).description("아지트 소개"),
				fieldWithPath("memberLimit").type(JsonFieldType.NUMBER).description("참가 인원 제한"),
				fieldWithPath("fee").type(JsonFieldType.NUMBER).description("참가비"),
				fieldWithPath("joinMethod").type(JsonFieldType.STRING).description("참가 방식"),
				fieldWithPath("birthYearMin").type(JsonFieldType.STRING).description("최소 년생 제한"),
				fieldWithPath("birthYearMax").type(JsonFieldType.STRING).description("최대 년생 제한"),
				fieldWithPath("genderRestriction").type(JsonFieldType.STRING).description("성별 제한"),
				fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("약속 날짜"),
				fieldWithPath("meetingTime").type(JsonFieldType.STRING).description("약속 시간"),
				fieldWithPath("isOnline").type(JsonFieldType.STRING).description("만남 타입"),
				fieldWithPath("location").type(JsonFieldType.STRING).description("오프라인 만남 장소"),
				fieldWithPath("joinQuestion").type(JsonFieldType.STRING).description("참가 신청 질문"),
				fieldWithPath("clubStatus").type(JsonFieldType.STRING).description("아지트 활성화 상태")
			).and(
				fieldWithPath("data.host").type(JsonFieldType.OBJECT).description("아지트 호스트 정보")
			)
			.andWithPrefix("data.host.",
				fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
				fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
				fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 이름"),
				fieldWithPath("fileInfo").type(JsonFieldType.OBJECT).description("회원 프로필 이미지")
			).andWithPrefix("data.host.fileInfo.",
				fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("파일 고유 식별자"),
				fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("파일 저장 경로"),
				fieldWithPath("fileName").type(JsonFieldType.STRING).description("파일 이름")
			).and(
				fieldWithPath("data.categorySmall").type(JsonFieldType.OBJECT).description("아지트 소분류 카테고리")
			).andWithPrefix("data.categorySmall.",
				fieldWithPath("categorySmallId").type(JsonFieldType.NUMBER).description("아지트 소분류 카테고리 고유 식별자"),
				fieldWithPath("categoryName").type(JsonFieldType.STRING).description("아지트 소분류 카테고리 이름").optional(),
				fieldWithPath("categoryLargeId").type(JsonFieldType.NUMBER)
					.description("아지트 대분류 카테고리 고유 식별자")
					.optional()
			).and(
				fieldWithPath("data.bannerImage").type(JsonFieldType.OBJECT).description("아지트 배너 이미지")
			).andWithPrefix("data.bannerImage.",
				fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("배너 이미지 고유 식별자"),
				fieldWithPath("fileName").type(JsonFieldType.STRING).description("배너 이미지 파일 이름"),
				fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("배너 이미지 파일 저장 경로")
			);
	}

	public static ResponseFieldsSnippet getMultiResponseSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터")
		).andWithPrefix("data[].",
			fieldWithPath("clubId").type(JsonFieldType.NUMBER).description("아지트 고유 식별자"),
			fieldWithPath("clubName").type(JsonFieldType.STRING).description("아지트 이름"),
			fieldWithPath("clubInfo").type(JsonFieldType.STRING).description("아지트 소개"),
			fieldWithPath("memberLimit").type(JsonFieldType.NUMBER).description("참가 인원 제한"),
			fieldWithPath("fee").type(JsonFieldType.NUMBER).description("참가비"),
			fieldWithPath("joinMethod").type(JsonFieldType.STRING).description("참가 방식"),
			fieldWithPath("birthYearMin").type(JsonFieldType.STRING).description("최소 년생 제한"),
			fieldWithPath("birthYearMax").type(JsonFieldType.STRING).description("최대 년생 제한"),
			fieldWithPath("genderRestriction").type(JsonFieldType.STRING).description("성별 제한"),
			fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("약속 날짜"),
			fieldWithPath("meetingTime").type(JsonFieldType.STRING).description("약속 시간"),
			fieldWithPath("isOnline").type(JsonFieldType.STRING).description("만남 타입"),
			fieldWithPath("location").type(JsonFieldType.STRING).description("오프라인 만남 장소"),
			fieldWithPath("joinQuestion").type(JsonFieldType.STRING).description("참가 신청 질문"),
			fieldWithPath("clubStatus").type(JsonFieldType.STRING).description("아지트 활성화 상태")
		).and(
			fieldWithPath("data[].host").type(JsonFieldType.OBJECT).description("아지트 호스트 정보")
		).andWithPrefix("data[].host.",
			fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
			fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
			fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 이름"),
			fieldWithPath("fileInfo").type(JsonFieldType.OBJECT).description("회원 프로필 이미지")
		).andWithPrefix("data[].host.fileInfo.",
			fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("배너 이미지 고유 식별자"),
			fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("배너 이미지 파일 저장 경로"),
			fieldWithPath("fileName").type(JsonFieldType.STRING).description("배너 이미지 파일 이름")
		).and(
			fieldWithPath("data[].categorySmall").type(JsonFieldType.OBJECT).description("아지트 소분류 카테고리")
		).andWithPrefix("data[].categorySmall.",
			fieldWithPath("categorySmallId").type(JsonFieldType.NUMBER).description("아지트 소분류 카테고리 고유 식별자"),
			fieldWithPath("categoryName").type(JsonFieldType.STRING).description("아지트 소분류 카테고리 이름").optional(),
			fieldWithPath("categoryLargeId").type(JsonFieldType.NUMBER)
				.description("아지트 대분류 카테고리 고유 식별자")
				.optional()
		).and(
			fieldWithPath("data[].bannerImage").type(JsonFieldType.OBJECT).description("아지트 배너 이미지")
		).andWithPrefix("data[].bannerImage.",
			fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("배너 이미지 고유 식별자"),
			fieldWithPath("fileName").type(JsonFieldType.STRING).description("배너 이미지 파일 이름"),
			fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("배너 이미지 파일 저장 경로")
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
