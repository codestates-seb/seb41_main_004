package com.codestates.azitserver.domain.stub;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;

public class MemberStubData {

	public static Member stubMember() {
		Member member = Member.builder()
			.memberId(1L)
			.avatar_image_id(1L)
			.email("stubmember@naver.com")
			.nickname("김스텁")
			.password("123456@asdf")
			.gender(Member.Gender.MALE)
			.birthYear("2001")
			.aboutMe("김스텁의 자기소개")
			.reputation(10)
			.memberStatus(Member.MemberStatus.ACTIVE)
			.build();
		return member;
	}

	public static MemberDto.Post stubMemberDtoPost() {
		MemberDto.Post post = new MemberDto.Post();

		post.setEmail("stubmember@naver.com");
		post.setNickname("김스텁");
		post.setPassword("123456@asdf");
		post.setPasswordCheck("123456@asdf");
		post.setGender(Member.Gender.MALE);
		post.setBirthYear("2001");
		post.setAboutMe("김스텁의 자기소개");

		return post;
	}

	public static MemberDto.Patch stubMemberDtoPatch() {
		MemberDto.Patch patch = new MemberDto.Patch();

		patch.setNickname("스텁김");
		patch.setPassword("654321@asdf");
		patch.setPasswordCheck("654321@asdf");
		patch.setAboutMe("김스텁의 자기소개");

		return patch;
	}

	public static MemberDto.Response stubMemberDtoResponse() {
		MemberDto.Response response = new MemberDto.Response();

		response.setMemberId(1L);
		response.setAvatarImageId(1L);
		response.setEmail("stubmember@naver.com");
		response.setNickname("김스텁");
		response.setPassword("123456@asdf");
		response.setGender(Member.Gender.MALE);
		response.setBirthYear("2001");
		response.setAboutMe("김스텁의 자기소개");
		response.setReputation(10);
		response.setMemberStatus(Member.MemberStatus.ACTIVE);

		return response;
	}

	public static Page<Member> stubMemberPage() {
		return new PageImpl<>(List.of(stubMember()), PageRequest.of(0, 10), 1);
	}
}
