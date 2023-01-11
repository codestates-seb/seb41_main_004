package com.codestates.azitserver.domain.category.controller;

import com.codestates.azitserver.global.utils.ControllerTestHelper;

public interface CategoryControllerTestHelper extends ControllerTestHelper {
	String CATEGORY_URL = "/api/categories";

	default String getCategoryUrl() {
		return CATEGORY_URL;
	}

}
