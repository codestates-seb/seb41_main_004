package com.codestates.azitserver.domain.review.dto;

import com.codestates.azitserver.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewDto {

    @Getter
    public static class Post {
        private Review.CommentCategory commentCategory;
        private String commentBody;
        private Boolean reviewStatus;
    }

    @Getter
    public static class Patch {
        private Review.CommentCategory commentCategory;
        private String commentBody;
        private Boolean reviewStatus;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long reviewId;
        private Long reviewer;
        private Long reviewee;
        private Long clubId;
        private Review.CommentCategory commentCategory;
        private String commentBody;
        private Boolean reviewStatus;
    }
}
