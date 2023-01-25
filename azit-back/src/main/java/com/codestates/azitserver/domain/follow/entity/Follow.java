package com.codestates.azitserver.domain.follow.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.codestates.azitserver.domain.common.Auditable;
import com.codestates.azitserver.domain.member.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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

	public void setFollower(Member member) {
		this.follower = member;
		if (!this.follower.getFollowerList().contains(this)) {
			this.follower.getFollowingList().add(this);
		}
	}

	public void setFollowee(Member member) {
		this.followee = member;
		if(!this.followee.getFollowingList().contains(this)) {
			this.followee.getFollowingList().add(this);
		}
	}

}
