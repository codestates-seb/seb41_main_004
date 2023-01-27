package com.codestates.azitserver.domain.follow.helper;

import com.codestates.azitserver.global.utils.ControllerTestHelper;

public interface FollowControllerTestHelper extends ControllerTestHelper {
	String DEFAULT_URL = "/api/members/{member-id}";

	default String getUrl(String... args) {
		StringBuilder url = new StringBuilder(DEFAULT_URL);
		for (String arg : args) {
			url.append("/").append(arg);
		}
		return String.valueOf(url);
	}
}
