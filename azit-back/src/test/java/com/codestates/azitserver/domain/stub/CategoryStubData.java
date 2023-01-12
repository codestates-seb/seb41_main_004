package com.codestates.azitserver.domain.stub;

import java.util.List;

import com.codestates.azitserver.domain.category.dto.CategoryDto;

public class CategoryStubData {
	public static CategoryDto.Response getResponseData() {
		CategoryDto.Response response = new CategoryDto.Response();
		response.setCategoryLargeId(1L);
		response.setCategoryName("문화/예술");
		response.setCategorySmall(
			List.of(getSmallResponse())
		);

		return response;
	}

	public static CategoryDto.LargeResponse getLargeResponse() {
		CategoryDto.LargeResponse response = new CategoryDto.LargeResponse();
		response.setCategoryLargeId(1L);
		response.setCategoryName("문화/예술");

		return response;
	}

	public static CategoryDto.SmallResponse getSmallResponse() {
		CategoryDto.SmallResponse response = new CategoryDto.SmallResponse();
		response.setCategorySmallId(1L);
		response.setCategoryName("전시");
		response.setCategoryLargeId(1L);

		return response;
	}
}
