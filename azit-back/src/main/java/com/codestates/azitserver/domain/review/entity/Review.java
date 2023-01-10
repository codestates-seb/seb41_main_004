package com.codestates.azitserver.domain.review.entity;

import com.codestates.azitserver.global.Audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Review extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Long reviewer; //TODO FK

    private Long reviewee; //TODO FK

    private Long clubId; //TODO FK

    //TODO 제약조건
    private CommentCategory commentCategory;
    private String commentBody;
    private Boolean reviewStatus; //default - false(숨기지 않음)

    public enum CommentCategory {
        CONSIDERATE("배려심이 있어요"),
        ACTIVELY_PARTICIPATE("적극적으로 모임에 참여해요"),
        KEEP_ON_TIME("시간 약속을 잘 지켜요"),
        HAVE_FUN_NEXT_TIME("다음에 만나면 더 재밌게 놀아요"),
        FEEL_COMFORTABLE("다른 사람을 편하게 해요"),
        MOOD_MAKER("분위기를 즐겁게 만들어요");

        private String commentCategory;

        CommentCategory(String commentCategory) {
            this.commentCategory = commentCategory;
        }
    }
}
