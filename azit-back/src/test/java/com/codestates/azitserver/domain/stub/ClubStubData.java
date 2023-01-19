package com.codestates.azitserver.domain.stub;

import static com.codestates.azitserver.domain.stub.CategoryStubData.*;
import static com.codestates.azitserver.domain.stub.MemberStubData.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

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
		club.setMeetingDate(LocalDate.now().plusDays(1));
		club.setMeetingTime(LocalTime.now().plusHours(1));
		club.setIsOnline("offline");
		club.setLocation("서울시 송파구");
		club.setJoinQuestion("재밌는 아지트 맞을까요?");
		club.setClubStatus(Club.ClubStatus.CLUB_ACTIVE);
		club.setFileInfo(FileInfoStubData.getDefaultFileInfo());

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
		post.setMeetingDate(LocalDate.now().plusDays(1));
		post.setMeetingTime(LocalTime.now().plusHours(1));
		post.setIsOnline("offline");
		post.setLocation("서울시 송파구");
		post.setJoinQuestion("재밌는 아지트 맞을까요?");
		post.setCategorySmallId(1L);

		return post;
	}

	public static ClubDto.Patch getClubDtoPatch() {
		ClubDto.Patch patch = new ClubDto.Patch();

		patch.setClubName("재밌는 아지트");
		patch.setClubInfo("재밌는 아지트입니다.");
		patch.setMemberLimit(4);
		patch.setFee(10000);
		patch.setBirthYearMin("1990");
		patch.setBirthYearMax("2000");
		patch.setGenderRestriction(Club.GenderRestriction.ALL);
		patch.setMeetingDate(LocalDate.now().plusDays(1));
		patch.setMeetingTime(LocalTime.now().plusHours(1));
		patch.setIsOnline("offline");
		patch.setLocation("서울시 송파구");

		return patch;
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
		response.setMeetingDate(LocalDate.now().plusDays(1));
		response.setMeetingTime(LocalTime.now().plusHours(1));
		response.setIsOnline("offline");
		response.setLocation("서울시 송파구");
		response.setJoinQuestion("재밌는 아지트 맞을까요?");
		response.setClubStatus(Club.ClubStatus.CLUB_ACTIVE);
		response.setHost(stubMemberIdAndNickname());
		response.setCategorySmall(getSmallResponse());
		response.setBannerImage(FileInfoStubData.getDefaultFileInfo());

		return response;
	}

	public static MockMultipartFile getMultipartJsonData(String content) {
		return new MockMultipartFile(
			"data",
			"",
			MediaType.APPLICATION_JSON_VALUE,
			content.getBytes()
		);
	}

	public static Page<Club> getClubPage() {
		return new PageImpl<>(List.of(getDefaultClub()), PageRequest.of(0, 10,
			Sort.by("createdAt").descending()), 1);
	}

	public static ClubDto.ReportClubResponse getClubDtoReportClubResponse() {
		ClubDto.ReportClubResponse reportClubResponse = new ClubDto.ReportClubResponse();

		reportClubResponse.setClubId(1L);
		reportClubResponse.setClubName("재밌는 아지트");
		reportClubResponse.setClubInfo("재밌는 아지트입니다.");
		reportClubResponse.setClubStatus(Club.ClubStatus.CLUB_ACTIVE);
		reportClubResponse.setHost(stubMemberIdAndNickname());
		reportClubResponse.setCategorySmall(getSmallResponse());

		return reportClubResponse;
	}
}
