package com.codestates.azitserver.domain.review.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.codestates.azitserver.domain.review.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReviewDto {

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post {
		@NotNull
		@Positive
		private Long reviewerId;

		@NotNull
		@Positive
		private Long revieweeId;

		@NotNull
		@Positive
		private Long clubId;

		private Review.CommentCategory commentCategory;

		@Length(max = 128)
		private String commentBody;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Response {
		private Long reviewId;
		private Long revieweeId;
		private Long clubId;
		private Review.CommentCategory commentCategory;
		private String commentBody;
		private Boolean reviewStatus;
		private LocalDateTime createdAt;
	}
}
