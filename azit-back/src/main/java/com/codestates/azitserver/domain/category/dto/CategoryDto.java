package com.codestates.azitserver.domain.category.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CategoryDto {
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Long categoryLargeId;
		private String categoryName;
		private List<SmallResponse> categorySmall;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class LargeResponse {
		private Long categoryLargeId;
		private String categoryName;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class SmallResponse {
		private Long categorySmallId;
		private String categoryName;
		private Long categoryLargeId;
	}
}
