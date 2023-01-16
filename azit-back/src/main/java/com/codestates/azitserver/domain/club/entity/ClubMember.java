package com.codestates.azitserver.domain.club.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.codestates.azitserver.domain.member.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ClubMember {
	public ClubMember(Member member, Club club, String joinAnswer) {
		this.member = member;
		this.club = club;
		this.joinAnswer = joinAnswer;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clubMemberId;

	@Column(nullable = false, length = 128)
	private String joinAnswer;

	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "CLUB_ID")
	private Club club;

	@Enumerated(EnumType.STRING)
	private ClubMemberStatus clubMemberStatus = ClubMemberStatus.CLUB_WAITING;

	public enum ClubMemberStatus {
		CLUB_WAITING(1, "승인 대기"),
		CLUB_JOINED(2, "참여 확정"),
		CLUB_REJECTED(3, "참여 거절"),
		CLUB_KICKED(4, "강퇴됨");

		@Getter
		private final int status;

		@Getter
		private final String description;

		ClubMemberStatus(int status, String description) {
			this.status = status;
			this.description = description;
		}
	}
}
