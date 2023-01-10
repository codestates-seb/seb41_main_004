package com.codestates.azitserver.domain.club.controller;

import com.codestates.azitserver.global.utils.ControllerTestHelper;

public interface ClubControllerTestHelper extends ControllerTestHelper {
	String CLUB_URL = "/api/clubs";

	String CLUB_RESOURCE_URI = "/{club-id}";

	default String getClubUrl() {
		return CLUB_URL;
	}

	default String getClubUri() {
		return CLUB_URL + CLUB_RESOURCE_URI;
	}
}
