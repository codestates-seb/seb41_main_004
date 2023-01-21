package com.codestates.azitserver.domain.review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.common.Auditable;
import com.codestates.azitserver.domain.member.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Review extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;

	@ManyToOne
	@JoinColumn(name = "REVIEWER_ID", updatable = false)
	private Member reviewer;

	@ManyToOne
	@JoinColumn(name = "REVIEWEE_ID", updatable = false)
	private Member reviewee;

	@ManyToOne
	@JoinColumn(name = "CLUB_ID", updatable = false)
	private Club club;

	@Enumerated(EnumType.STRING)
	private CommentCategory commentCategory;

	@Column(length = 128)
	private String commentBody;

	@Column(nullable = false)
	private Boolean reviewStatus = false; //default - false(숨기지 않음)

	public enum CommentCategory {
		CONSIDERATE("배려심이 있어요"),
		ACTIVELY_PARTICIPATE("적극적으로 모임에 참여해요"),
		KEEP_ON_TIME("시간 약속을 잘 지켜요"),
		HAVE_FUN_NEXT_TIME("다음에 만나면 더 재밌게 놀아요"),
		FEEL_COMFORTABLE("다른 사람을 편하게 해요"),
		MOOD_MAKER("분위기를 즐겁게 만들어요");

		@Getter
		private final String commentCategory;

		CommentCategory(String commentCategory) {
			this.commentCategory = commentCategory;
		}
	}
}
