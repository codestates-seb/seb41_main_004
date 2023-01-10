package com.codestates.azitserver.domain.club.controller;

import com.codestates.azitserver.global.utils.ControllerTestHelper;

public interface ClubControllerTestHelper extends ControllerTestHelper {
	String CLUB_URL = "/api/clubs";

	default String getClubUrl() {
		return CLUB_URL;
	}
}
