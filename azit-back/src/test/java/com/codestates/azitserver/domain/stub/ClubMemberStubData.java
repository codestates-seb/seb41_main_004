package com.codestates.azitserver.domain.stub;

import static com.codestates.azitserver.domain.stub.CategoryStubData.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.codestates.azitserver.domain.club.dto.ClubDto;
import com.codestates.azitserver.domain.club.dto.ClubMemberDto;
import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.member.dto.MemberDto;

public class ClubMemberStubData {
	public static ClubMember getDefaultClubMember() {
		ClubMember clubMember = new ClubMember();

		clubMember.setClubMemberId(1L);
		clubMember.setJoinAnswer("저도 참가할래요!");
		clubMember.setMember(MemberStubData.stubMember());
		clubMember.setClub(ClubStubData.getDefaultClub());
		clubMember.setClubMemberStatus(ClubMember.ClubMemberStatus.CLUB_WAITING);

		return clubMember;
	}

	public static ClubMemberDto.Signup getClubMemberSignup() {
		ClubMemberDto.Signup signup = new ClubMemberDto.Signup();

		signup.setMemberId(1L);
		signup.setJoinAnswer("저도 참가할래요!");

		return signup;
	}


	public static ClubMemberDto.Patch getClubMemberPatch() {
		ClubMemberDto.Patch patch = new ClubMemberDto.Patch();

		patch.setStatus(String.valueOf(ClubMember.ClubMemberStatus.CLUB_JOINED));

		return patch;
	}

	public static ClubMemberDto.Response getClubMemberDtoResponse() {
		ClubMemberDto.Response response = new ClubMemberDto.Response();

		response.setClubMemberId(1L);
		response.setMember(MemberStubData.getClubMemberMemberResponse());
		response.setJoinAnswer("저도 참가할래요!");
		response.setClubMemberStatus(ClubMember.ClubMemberStatus.CLUB_WAITING);

		return response;
	}

	public static Page<ClubMember> getClubMemberPage() {
		return new PageImpl<>(List.of(getDefaultClubMember()), PageRequest.of(0, 10,
			Sort.by("createdAt").descending()), 1);
	}

	public static ClubMemberDto.ClubMemberStatusResponse getClubMemberStatusDtoResponse(Long index) {
		ClubMemberDto.ClubMemberStatusResponse response = new ClubMemberDto.ClubMemberStatusResponse();

		response.setClubMemberId(index);
		response.setClubInfoResponse(getClubMemberStatusClubInfoResponse());
		response.setClubMemberStatus(ClubMember.ClubMemberStatus.CLUB_WAITING);
		response.setIsHost(false);
		response.setIsHidden(false);
		response.setIsReviewed(false);

		return response;
	}

	public static ClubDto.ClubMemberStatusClubInfoResponse getClubMemberStatusClubInfoResponse() {
		ClubDto.ClubMemberStatusClubInfoResponse response = new ClubDto.ClubMemberStatusClubInfoResponse();

		response.setClubId(1L);
		response.setClubName("재밌는 아지트");
		response.setClubInfo("재밌는 아지트입니다.");
		response.setMemberLimit(4);
		response.setMeetingDate(LocalDate.now().plusDays(1));
		response.setMeetingTime(LocalTime.now().plusHours(1));
		response.setIsOnline("offline");
		response.setLocation("서울시 송파구");
		response.setClubStatus(Club.ClubStatus.CLUB_ACTIVE);
		response.setHost(MemberStubData.stubMemberIdAndNickname());
		response.setCategorySmall(getSmallResponse());
		response.setBannerImage(FileInfoStubData.getFileInfoDtoResponse());
		response.setParticipantList(List.of(getParticipantResponse(1L), getParticipantResponse(2L),
			getParticipantResponse(3L)));

		return response;
	}

	public static MemberDto.ParticipantResponse getParticipantResponse(Long index) {
		MemberDto.ParticipantResponse response = new MemberDto.ParticipantResponse();

		response.setMemberId(index);
		response.setNickname("참여자"+index);
		response.setFileInfo(FileInfoStubData.getFileInfoDtoResponse());

		return response;
	}
}
