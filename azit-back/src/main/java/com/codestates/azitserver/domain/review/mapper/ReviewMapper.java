package com.codestates.azitserver.domain.review.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codestates.azitserver.domain.review.dto.ReviewDto;
import com.codestates.azitserver.domain.review.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
	@Mapping(source = "reviewerId", target = "reviewer.memberId")
	@Mapping(source = "revieweeId", target = "reviewee.memberId")
	@Mapping(source = "clubId", target = "club.clubId")
	Review reviewDtoPostToReview(ReviewDto.Post post);

	@Mapping(source = "reviewee.memberId", target = "revieweeId")
	@Mapping(source = "club.clubId", target = "clubId")
	ReviewDto.Response reviewToReviewDtoResponse(Review review);
}
