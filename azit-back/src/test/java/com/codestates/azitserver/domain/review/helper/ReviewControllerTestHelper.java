package com.codestates.azitserver.domain.review.helper;

import com.codestates.azitserver.global.utils.ControllerTestHelper;

public interface ReviewControllerTestHelper extends ControllerTestHelper {
	String REVIEW_URL = "/api/reviews";

	String REVIEW_RESOURCE_URI = "/{review-id}";

	default String getReviewUrl() {
		return REVIEW_URL;
	}

	default String getReviewUri() {
		return REVIEW_URL + REVIEW_RESOURCE_URI;
	}
}
