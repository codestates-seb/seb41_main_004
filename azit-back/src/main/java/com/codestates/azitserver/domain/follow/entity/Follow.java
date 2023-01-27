package com.codestates.azitserver.domain.follow.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.codestates.azitserver.domain.common.Auditable;
import com.codestates.azitserver.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Follow extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long followId;

	@ManyToOne
	@JoinColumn(name = "FOLLOWER_ID")
	private Member follower;

	@ManyToOne
	@JoinColumn(name = "FOLLOWEE_ID")
	private Member followee;

	private Boolean matpal;

	public void setFollower(Member member) {
		this.follower = member;
		if (!this.follower.getFollowerList().contains(this)) {
			this.follower.getFollowingList().add(this);
		}
	}

	public void setFollowee(Member member) {
		this.followee = member;
		if (!this.followee.getFollowingList().contains(this)) {
			this.followee.getFollowingList().add(this);
		}
	}

	public Boolean getMatpal() {
		return matpal != null;
	}

	//*** Builder ***//
	private Follow(Builder builder) {
		this.follower = builder.follower;
		this.followee = builder.followee;
	}

	public static class Builder {
		private final Member follower;
		private final Member followee;

		private Boolean matpal;

		public Builder(Member follower, Member followee) {
			this.follower = follower;
			this.followee = followee;
		}

		public Builder matpal(Boolean matpal) {
			this.matpal = matpal;
			return this;
		}

		public Follow build() {
			return new Follow(this);
		}
	}
}
