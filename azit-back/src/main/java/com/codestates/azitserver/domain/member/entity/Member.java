package com.codestates.azitserver.domain.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.codestates.azitserver.domain.common.Auditable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Member extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column(name = "AVATAR_IMAGE_ID", unique = true) //TODO FK
	private Long avatar_image_id;

	@Column(nullable = false, unique = true, length = 128)
	private String email;

	@Column(nullable = false, unique = true, length = 16)
	private String nickname;

	@Column(nullable = false)
	private String password;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Column(name = "BIRTH_YEAR", length = 4)
	private String birthYear;

	@Column(name = "ABOUT_ME", length = 128)
	private String aboutMe;

	private Integer reputation;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "MEMBER_STATUS")
	private MemberStatus memberStatus;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<>();

	public enum Gender {
		MALE("남자"),
		FEMALE("여자");

		private String gender;

		Gender(String gender) {
			this.gender = gender;
		}
	}

	public enum MemberStatus {
		ACTIVE("활성"),
		DELETED("탈퇴");

		private String memberStatus;

		MemberStatus(String memberStatus) {
			this.memberStatus = memberStatus;
		}
	}

	@Builder
	public Member(Long memberId, Long avatar_image_id, String email, String nickname,
		String password, Gender gender, String birthYear, String aboutMe,
		Integer reputation, MemberStatus memberStatus) {
		//        Assert.hasText(email, "email must not be empty");
		//        Assert.hasText(nickname, "nickname must not be empty");
		//        Assert.hasText(password, "password must not be empty");
		//        Assert.notNull(gender, "gender must not be empty");
		//        Assert.notNull(reputation, "reputation must not be empty");
		//        Assert.notNull(memberStatus, "memberStatus must not be empty");

		this.memberId = memberId;
		this.avatar_image_id = avatar_image_id;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.gender = gender;
		this.birthYear = birthYear;
		this.aboutMe = aboutMe;
		this.reputation = reputation;
		this.memberStatus = memberStatus;
	}

}
