package com.codestates.azitserver.domain.stub;

import java.time.LocalDateTime;

import com.codestates.azitserver.domain.club.dto.ClubDto;
import com.codestates.azitserver.domain.club.entity.Club;

public class ClubStubData {
	public static Club getDefaultClub() {
		Club club = new Club();

		club.setClubId(1L);
		club.setClubName("재밌는 아지트");
		club.setClubInfo("재밌는 아지트입니다.");
		club.setMemberLimit(4);
		club.setFee(10000);
		club.setJoinMethod(Club.JoinMethod.APPROVAL);
		club.setBirthYearMin("1990");
		club.setBirthYearMax("2000");
		club.setGenderRestriction(Club.GenderRestriction.ALL);
		club.setMeetingDate(LocalDateTime.now().plusDays(1));
		club.setIsOnline(false);
		club.setLocation("서울시 송파구");
		club.setJoinQuestion("재밌는 아지트 맞을까요?");
		club.setClubStatus(Club.ClubStatus.CLUB_ACTIVE);

		return club;
	}

	public static ClubDto.Post getClubDtoPost() {
		ClubDto.Post post = new ClubDto.Post();
		post.setClubName("재밌는 아지트");
		post.setClubInfo("재밌는 아지트입니다.");
		post.setMemberLimit(4);
		post.setFee(10000);
		post.setJoinMethod(Club.JoinMethod.APPROVAL);
		post.setBirthYearMin("1990");
		post.setBirthYearMax("2000");
		post.setGenderRestriction(Club.GenderRestriction.ALL);
		post.setMeetingDate(LocalDateTime.now().plusDays(1));
		post.setIsOnline(false);
		post.setLocation("서울시 송파구");
		post.setJoinQuestion("재밌는 아지트 맞을까요?");

		return post;
	}

	public static ClubDto.Response getClubDtoResponse() {
		ClubDto.Response response = new ClubDto.Response();

		response.setClubId(1L);
		response.setClubName("재밌는 아지트");
		response.setClubInfo("재밌는 아지트입니다.");
		response.setMemberLimit(4);
		response.setFee(10000);
		response.setJoinMethod(Club.JoinMethod.APPROVAL);
		response.setBirthYearMin("1990");
		response.setBirthYearMax("2000");
		response.setGenderRestriction(Club.GenderRestriction.ALL);
		response.setMeetingDate(LocalDateTime.now().plusDays(1));
		response.setIsOnline(false);
		response.setLocation("서울시 송파구");
		response.setJoinQuestion("재밌는 아지트 맞을까요?");
		response.setClubStatus(Club.ClubStatus.CLUB_ACTIVE);

		return response;
	}
}
