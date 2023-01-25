package com.codestates.azitserver.domain.stub;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.codestates.azitserver.domain.club.dto.ClubMemberDto;
import com.codestates.azitserver.domain.club.entity.ClubMember;

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

}
