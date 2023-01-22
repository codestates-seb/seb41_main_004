package com.codestates.azitserver.domain.stub;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.codestates.azitserver.domain.review.dto.ReviewDto;
import com.codestates.azitserver.domain.review.entity.Review;

public class ReviewStubData {
	public static Review getDefaultReview() {
		Review review = new Review();

		review.setReviewId(1L);
		review.setReviewer(MemberStubData.stubMember());
		review.setReviewee(MemberStubData.stubMember());
		review.setClub(ClubStubData.getDefaultClub());
		review.setCommentCategory(Review.CommentCategory.CONSIDERATE);
		review.setCommentBody("마음씨가 너무 착해요.");
		review.setReviewStatus(false);

		return review;
	}

	public static ReviewDto.Post getReviewDtoPost() {
		ReviewDto.Post post = new ReviewDto.Post();

		post.setReviewerId(1L);
		post.setRevieweeId(2L);
		post.setClubId(3L);
		post.setCommentCategory(Review.CommentCategory.CONSIDERATE);
		post.setCommentBody("마음씨가 너무 착해요.");

		return post;
	}

	public static ReviewDto.Patch getReviewDtoPatch() {
		ReviewDto.Patch patch = new ReviewDto.Patch();

		patch.setReviewStatus(true);

		return patch;
	}

	public static ReviewDto.Get getReviewDtoGet() {
		ReviewDto.Get get = new ReviewDto.Get();

		get.setRevieweeId(2L);

		return get;
	}

	public static ReviewDto.Response getReviewDtoResponse() {
		ReviewDto.Response response = new ReviewDto.Response();

		response.setReviewId(1L);
		response.setRevieweeId(2L);
		response.setClub(ClubStubData.getClubDtoReviewClubResponse());
		response.setCommentCategory(Review.CommentCategory.CONSIDERATE.getCommentCategory());
		response.setCommentBody("마음씨가 너무 착해요.");
		response.setReviewStatus(false);

		return response;
	}

	public static Page<Review> getReviewPage() {
		return new PageImpl<>(List.of(getDefaultReview()), PageRequest.of(0, 10,
			Sort.by("createdAt").descending()), 1);
	}
}
