package com.codestates.azitserver.domain.club.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
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
				.description("참가 방식 [ APPROVAL | FIRST_COME ]"),
			fieldWithPath("birthYearMin").type(JsonFieldType.STRING).description("최소 년생 제한"),
			fieldWithPath("birthYearMax").type(JsonFieldType.STRING).description("최대 년생 제한"),
			fieldWithPath("genderRestriction").type(JsonFieldType.STRING)
				.description("성별 제한 [ MALE_ONLY | FEMALE_ONLY | ALL ]"),
			fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("날짜 및 시간"),
			fieldWithPath("isOnline").type(JsonFieldType.BOOLEAN)
				.description("만남 타입 [ true : 온라인 | false : 오프라인 ]"),
			fieldWithPath("location").type(JsonFieldType.STRING).description("오프라인 만남 장소"),
			fieldWithPath("joinQuestion").type(JsonFieldType.STRING).description("참가 신청 질문")
		);
	}

	public static RequestPartFieldsSnippet getPatchRequestPartFieldsSnippet() {
		return requestPartFields("data",
			fieldWithPath("clubId").type(JsonFieldType.NUMBER).description("아지트 고유 식별자").optional(),
			fieldWithPath("clubName").type(JsonFieldType.STRING).description("아지트 이름").optional(),
			fieldWithPath("clubInfo").type(JsonFieldType.STRING).description("아지트 소개").optional(),
			fieldWithPath("memberLimit").type(JsonFieldType.NUMBER).description("참가 인원 제한").optional(),
			fieldWithPath("fee").type(JsonFieldType.NUMBER).description("참가비").optional(),
			fieldWithPath("birthYearMin").type(JsonFieldType.STRING).description("최소 년생 제한").optional(),
			fieldWithPath("birthYearMax").type(JsonFieldType.STRING).description("최대 년생 제한").optional(),
			fieldWithPath("genderRestriction").type(JsonFieldType.STRING)
				.description("성별 제한 [ MALE_ONLY | FEMALE_ONLY | ALL ]")
				.optional(),
			fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("날짜 및 시간").optional(),
			fieldWithPath("isOnline").type(JsonFieldType.BOOLEAN)
				.description("만남 타입 [ true : 온라인 | false : 오프라인 ]")
				.optional(),
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
			fieldWithPath("joinMethod").type(JsonFieldType.STRING).description("참가 방식 [ APPROVAL | FIRST_COME ]"),
			fieldWithPath("birthYearMin").type(JsonFieldType.STRING).description("최소 년생 제한"),
			fieldWithPath("birthYearMax").type(JsonFieldType.STRING).description("최대 년생 제한"),
			fieldWithPath("genderRestriction").type(JsonFieldType.STRING)
				.description("성별 제한 [ MALE_ONLY | FEMALE_ONLY | ALL ]"),
			fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("날짜 및 시간"),
			fieldWithPath("isOnline").type(JsonFieldType.BOOLEAN).description("만남 타입 [ true : 온라인 | false : 오프라인 ]"),
			fieldWithPath("location").type(JsonFieldType.STRING).description("오프라인 만남 장소"),
			fieldWithPath("joinQuestion").type(JsonFieldType.STRING).description("참가 신청 질문"),
			fieldWithPath("clubStatus").type(JsonFieldType.STRING)
				.description("아지트 활성화 상태 [ CLUB_ACTIVE | CLUB_CANCEL ]")
		);
	}
}