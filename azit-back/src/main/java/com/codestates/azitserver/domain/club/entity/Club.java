package com.codestates.azitserver.domain.club.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.codestates.azitserver.domain.category.entity.CategorySmall;
import com.codestates.azitserver.domain.common.Auditable;
import com.codestates.azitserver.domain.fileInfo.entity.FileInfo;
import com.codestates.azitserver.domain.member.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Club extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clubId;

	@Column(name = "CLUB_NAME", nullable = false, length = 24)
	private String clubName;

	@Column(name = "CLUB_INFO", nullable = false, length = 128)
	private String clubInfo;

	@Column(name = "MEMBER_LIMIT", nullable = false)
	private Integer memberLimit;

	@Column(name = "FEE", nullable = false)
	private Integer fee = 0;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "JOIN_METHOD", nullable = false, updatable = false, length = 24)
	private JoinMethod joinMethod = JoinMethod.APPROVAL;

	@Column(name = "BIRTH_YEAR_MIN", length = 4)
	private String birthYearMin;

	@Column(name = "BIRTH_YEAR_MAX", length = 4)
	private String birthYearMax;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "GENDER_RESTRICTION", nullable = false)
	private GenderRestriction genderRestriction;

	@Column(name = "MEETING_DATE", nullable = false)
	private LocalDate meetingDate;

	@Column(name = "MEETING_TIME", nullable = false)
	private LocalTime meetingTime;

	@Column(name = "IS_ONLINE", nullable = false)
	private String isOnline;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "JOIN_QUESTION", nullable = false, updatable = false, length = 24)
	private String joinQuestion;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "CLUB_STATUS", nullable = false)
	private ClubStatus clubStatus = ClubStatus.CLUB_ACTIVE;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORY_SMALL_ID", updatable = false)
	private CategorySmall categorySmall;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "FILE_INFO_ID")
	private FileInfo fileInfo;

	@OneToOne
	@JoinColumn(name = "HOST_ID", updatable = false)
	private Member host;

	@OneToMany(mappedBy = "club", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ClubMember> clubMembers = new ArrayList<>();

	public ClubMember addClubMember(ClubMember clubMember) {
		this.clubMembers.add(clubMember);
		if (clubMember.getClub() != this) {
			clubMember.setClub(this);
		}

		return clubMember;
	}

	// TODO: banner_image, host_id, club_members

	public enum JoinMethod {
		APPROVAL("승인제"),
		FIRST_COME("선착순");

		@Getter
		private final String method;

		JoinMethod(String method) {
			this.method = method;
		}
	}

	public enum GenderRestriction {
		MALE_ONLY("남자만"),
		FEMALE_ONLY("여자만"),
		ALL("상관없음");

		@Getter
		private final String restrictionType;

		GenderRestriction(String restrictionType) {
			this.restrictionType = restrictionType;
		}
	}

	public enum ClubStatus {
		CLUB_ACTIVE("아지트 활동중"),
		CLUB_CANCEL("아지트 취소됨");

		@Getter
		private final String status;

		ClubStatus(String status) {
			this.status = status;
		}
	}
}
