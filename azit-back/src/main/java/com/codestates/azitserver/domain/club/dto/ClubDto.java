package com.codestates.azitserver.domain.club.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.codestates.azitserver.domain.club.entity.Club;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ClubDto {
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post {
		@NotBlank(message = "The club name must not be null")
		@Length(max = 24, message = "The club name must be less than 24 characters.")
		private String clubName;

		@NotBlank
		@Length(max = 128, message = "The club information must be less than 128 characters.")
		private String clubInfo;

		@NotNull
		@Range(min = 3, max = 20, message = "The participant limit is between 3 and 20")
		private Integer memberLimit;

		@NotNull
		@PositiveOrZero(message = "The participation fee cannot be negative. ")
		private Integer fee;

		@NotNull
		private Club.JoinMethod joinMethod;

		@Length(min = 4, max = 4, message = "Birth year must be 4 digit number.")
		@Pattern(regexp = "\\d{4}", message = "The birth year must be 4 digit number.")
		private String birthYearMin;

		@Length(min = 4, max = 4, message = "Birth year must be 4 digit number.")
		@Pattern(regexp = "\\d{4}", message = "The birth year must be 4 digit number.")
		private String birthYearMax;

		@NotNull
		private Club.GenderRestriction genderRestriction;

		@NotNull
		@FutureOrPresent(message = "Appointment date cannot be in the past." )
		private LocalDateTime meetingDate;

		@NotNull
		private Boolean isOnline;

		private String location;

		@NotNull
		@Length(max = 24, message = "The join question must be less than 24 characters.")
		private String joinQuestion;

		// TODO : 연관관계 매핑

		// private Long hostId;
		// private List<Long> categories;
	}

	@Getter
	@Setter
	public static class Response {
		private Long clubId;
		private String clubName;
		private String clubInfo;
		private Integer memberLimit;
		private Integer fee;
		private Club.JoinMethod joinMethod;
		private String birthYearMin;
		private String birthYearMax;
		private Club.GenderRestriction genderRestriction;
		private LocalDateTime meetingDate;
		private Boolean isOnline;
		private String location;
		private String joinQuestion;
		private Club.ClubStatus clubStatus;
	}
}
