package com.codestates.azitserver.domain.member.controller.descriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.RequestPartFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

public class MemberFieldDescriptor {
	public static RequestPartFieldsSnippet getPostRequestPartFieldsSnippet() {
		return requestPartFields(
			"data",
			fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
			fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
			fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
			fieldWithPath("passwordCheck").type(JsonFieldType.STRING).description("비밀번호 확인"),
			fieldWithPath("gender").type(JsonFieldType.STRING).description("성별")
				.attributes(key("constraints").value("MALE | FEMALE")),
			fieldWithPath("birthYear").type(JsonFieldType.STRING).description("출생년도"),
			fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기소개").optional(),
			// subsectionWithPath("data[].categorySmallIdList").type(JsonFieldType.ARRAY)
			// 	.description("카테고리(소) 리스트 섹션"),
			fieldWithPath("categorySmallId").type(JsonFieldType.ARRAY)
				.description("카테고리(소) 고유 식별자")

		);
	}

	public static RequestFieldsSnippet getPatchRequestFieldsSnippet() {
		return requestFields(
			fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자").optional(),
			fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
			// fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
			// fieldWithPath("passwordCheck").type(JsonFieldType.STRING).description("비밀번호 확인"),
			fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기소개").optional(),
			fieldWithPath("categorySmallId").type(JsonFieldType.ARRAY).description("관심 카테고리(소)")
		);
	}

	public static ResponseFieldsSnippet getSingleResponseSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터")
		).andWithPrefix("data.",
			fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
			fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
			fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
			//	fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
			fieldWithPath("gender").type(JsonFieldType.STRING).description("성별")
				.attributes(key("constraints").value("MALE | FEMALE")),
			fieldWithPath("birthYear").type(JsonFieldType.STRING).description("출생년도"),
			fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기소개").optional(),
			fieldWithPath("reputation").type(JsonFieldType.NUMBER).description("평판 점수")
				.attributes(key("constraints").value("기본값 10")),
			fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태")
				.attributes(key("constraints").value("ACTIVE: 활성 | DELETED: 탈퇴")),
			// subsectionWithPath("categorySmallIdList").type(JsonFieldType.ARRAY)
			// 	.description("카테고리(소) 리스트 섹션"),
			fieldWithPath("categorySmallIdList").type(JsonFieldType.ARRAY).description("카테고리(소) 고유 식별자"),
			fieldWithPath("follower").type(JsonFieldType.NUMBER).description("나를 팔로우하고있는 사람 수"),
			fieldWithPath("following").type(JsonFieldType.NUMBER).description("내가 팔로우하고있는 사람 수")
		).and(
			fieldWithPath("data.fileInfo").type(JsonFieldType.OBJECT).description("프로필 사진")
		).andWithPrefix("data.fileInfo.",
			fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("프로필 사진 고유 식별자"),
			fieldWithPath("fileName").type(JsonFieldType.STRING).description("프로필 사진 파일명"),
			fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("프로핀 사진 파일 경로")
		);
	}

	public static ResponseFieldsSnippet getMultiResponseSnippet() {
		return responseFields(
			fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터")
		).andWithPrefix("data[].",
				fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
				fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
				fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
				//	fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
				fieldWithPath("gender").type(JsonFieldType.STRING).description("성별")
					.attributes(key("constraints").value("MALE | FEMALE")),
				fieldWithPath("birthYear").type(JsonFieldType.STRING).description("출생년도"),
				fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기소개").optional(),
				fieldWithPath("reputation").type(JsonFieldType.STRING).description("평판 점수")
					.attributes(key("constraints").value("기본값 10")),
				fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태")
					.attributes(key("constraints").value("ACTIVE: 활성 | DELETED: 탈퇴")),
				fieldWithPath("memberCategory").type(JsonFieldType.ARRAY).description("회원-카테고리 연관테이블")
			).and(
				fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보")
			).andWithPrefix("pageInfo.",
				fieldWithPath("page").type(JsonFieldType.NUMBER).description("요청한 페이지"),
				fieldWithPath("size").type(JsonFieldType.NUMBER).description("요청한 개수"),
				fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
				fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 개수")
			).and(fieldWithPath("data[].fileInfo").type(JsonFieldType.OBJECT).description("프로필 사진"))
			.andWithPrefix("data[].fileInfo.",
				fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("프로필 사진 고유 식별자"),
				fieldWithPath("fileName").type(JsonFieldType.STRING).description("프로필 사진 파일명"),
				fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("프로핀 사진 파일 경로")
			);
	}

	public static RequestFieldsSnippet getNickCheckFieldsSnippet() {
		return requestFields(
			fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
		);
	}

	public static RequestFieldsSnippet getEmailCheckFieldsSnippet() {
		return requestFields(
			fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
		);
	}

	public static ResponseFieldsSnippet getMultiMyDetailsResponseSnippet() {
		return responseFields(
				fieldWithPath("[].clubMemberId").type(JsonFieldType.NUMBER)
					.description("아지트-회원 연관 테이블 엔티티 고유 식별자"))
			.and(fieldWithPath("[].clubInfoResponse").type(JsonFieldType.OBJECT).description("아지트 정보 객체"))
			.andWithPrefix("[].clubInfoResponse.",
				fieldWithPath("clubId").type(JsonFieldType.NUMBER).description("아지트 고유 식별자"),
				fieldWithPath("clubName").type(JsonFieldType.STRING).description("아지트 이름"),
				fieldWithPath("clubInfo").type(JsonFieldType.STRING).description("아지트 소개"),
				fieldWithPath("memberLimit").type(JsonFieldType.NUMBER).description("참가 인원 제한"),
				fieldWithPath("meetingDate").type(JsonFieldType.STRING).description("약속 날짜"),
				fieldWithPath("meetingTime").type(JsonFieldType.STRING).description("약속 시간"),
				fieldWithPath("isOnline").type(JsonFieldType.STRING)
					.description("만남 타입").attributes(key("constraints").value("online : 온라인 | offline : 오프라인")),
				fieldWithPath("location").type(JsonFieldType.STRING).description("오프라인 만남 장소")
					.attributes(key("constraints").value("오프라인 만남인 경우에만 입력")).optional(),
				fieldWithPath("clubStatus").type(JsonFieldType.STRING).description("아지트 활성화 상태")
			).and(
				fieldWithPath("[].clubInfoResponse.host").type(JsonFieldType.OBJECT).description("아지트 호스트 정보")
			)
			.andWithPrefix("[].clubInfoResponse.host.",
				fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
				fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
				fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 이름"),
				fieldWithPath("fileInfo").type(JsonFieldType.OBJECT).description("회원 프로필 이미지")
			).andWithPrefix("[].clubInfoResponse.host.fileInfo.",
				fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("파일 고유 식별자"),
				fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("파일 저장 경로"),
				fieldWithPath("fileName").type(JsonFieldType.STRING).description("파일 이름")
			).and(
				fieldWithPath("[].clubInfoResponse.participantList.[]").type(JsonFieldType.ARRAY).description("아지트 호스트 정보")
			)
			.andWithPrefix("[].clubInfoResponse.participantList.[].",
				fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유 식별자"),
				fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 이름"),
				fieldWithPath("fileInfo").type(JsonFieldType.OBJECT).description("회원 프로필 이미지")
			).andWithPrefix("[].clubInfoResponse.participantList.[].fileInfo.",
				fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("파일 고유 식별자"),
				fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("파일 저장 경로"),
				fieldWithPath("fileName").type(JsonFieldType.STRING).description("파일 이름"))
			.and(
				fieldWithPath("[].clubInfoResponse.categorySmall").type(JsonFieldType.OBJECT).description("아지트 소분류 카테고리")
			).andWithPrefix("[].clubInfoResponse.categorySmall.",
				fieldWithPath("categorySmallId").type(JsonFieldType.NUMBER).description("아지트 소분류 카테고리 고유 식별자"),
				fieldWithPath("categoryName").type(JsonFieldType.STRING).description("아지트 소분류 카테고리 이름").optional(),
				fieldWithPath("categoryLargeId").type(JsonFieldType.NUMBER)
					.description("아지트 대분류 카테고리 고유 식별자")
					.optional()
			).and(
				fieldWithPath("[].clubInfoResponse.bannerImage").type(JsonFieldType.OBJECT).description("아지트 배너 이미지")
			).andWithPrefix("[].clubInfoResponse.bannerImage.",
				fieldWithPath("fileId").type(JsonFieldType.NUMBER).description("배너 이미지 고유 식별자"),
				fieldWithPath("fileName").type(JsonFieldType.STRING).description("배너 이미지 파일 이름"),
				fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("배너 이미지 파일 저장 경로")

			)
			.and(fieldWithPath("[].clubMemberStatus").type(JsonFieldType.STRING).description("참여 상태"),
				fieldWithPath("[].isHost").type(JsonFieldType.BOOLEAN).description("호스트 여부"),
				fieldWithPath("[].isHidden").type(JsonFieldType.BOOLEAN).description("숨김 여부"),
				fieldWithPath("[].isReviewed").type(JsonFieldType.BOOLEAN).description("리뷰 여부")
			);

	}
}
