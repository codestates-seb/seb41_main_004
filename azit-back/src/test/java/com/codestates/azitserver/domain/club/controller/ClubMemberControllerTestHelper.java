package com.codestates.azitserver.domain.club.controller;

import com.codestates.azitserver.global.utils.ControllerTestHelper;

public interface ClubMemberControllerTestHelper extends ControllerTestHelper {
	String CLUB_MEMBER_URL = "/api/clubs/{club-id}";

	default String getClubMemberUrl(String ...args) {
		StringBuilder url = new StringBuilder(CLUB_MEMBER_URL);
		for (String arg : args) {
			url.append("/").append(arg);
		}
		return String.valueOf(url);
	}
}
