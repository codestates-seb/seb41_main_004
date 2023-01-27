package com.codestates.azitserver.domain.stub;

import static com.codestates.azitserver.domain.stub.ClubStubData.*;
import static com.codestates.azitserver.domain.stub.MemberStubData.*;

import com.codestates.azitserver.domain.club.dto.ClubReportDto;
import com.codestates.azitserver.domain.club.entity.ClubReport;

public class ClubReportStubData {
	public static ClubReportDto.Post getClubReportPost() {
		ClubReportDto.Post post = new ClubReportDto.Post();

		post.setReportCategory(String.valueOf(ClubReport.Category.ETC));
		post.setReportReason("내 맘에 안 듬ㅋㅋ");

		return post;
	}

	public static ClubReportDto.Response getClubReportResponse() {
		ClubReportDto.Response response = new ClubReportDto.Response();

		response.setClubReportId(1L);
		response.setReportCategory(ClubReport.Category.ETC);
		response.setReportReason("내 맘에 안 듬ㅋㅋ");
		response.setClub(getClubDtoReportClubResponse());
		response.setMember(stubMemberIdAndNickname());

		return response;
	}
}
