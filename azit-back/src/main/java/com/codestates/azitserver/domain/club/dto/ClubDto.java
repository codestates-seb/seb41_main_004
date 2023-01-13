package com.codestates.azitserver.domain.club.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.codestates.azitserver.domain.category.dto.CategoryDto;
import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.global.validator.NotSpace;

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

		@NotNull
		@Positive
		private Long categorySmallId;

		// TODO : 연관관계 매핑

		// private Long hostId;
		// private String bannerImageUrl;
	}

	/**
	 * - 항시수정 가능항목 : clubInfo, bannerImageUrl, genderRestriction, birthYearMin, birthYearMax, fee
	 * <br>
	 * - 수정 제한 항목(신청자 또는 참가자 있을시) : clubName, memberLimit, meetingDate, isOnline,location
	 * <br>
	 * - 수정 불가 항목 : joinQuestion, categories, joinMethod
	 */
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Patch {
		private Long clubId;

		@NotSpace(message = "The club name must not be empty space")
		@Length(max = 24, message = "The club name must be less than 24 characters.")
		private String clubName;

		@NotSpace(message = "The club information must not be empty space")
		@Length(max = 128, message = "The club information must be less than 128 characters.")
		private String clubInfo;

		@Range(min = 3, max = 20, message = "The participant limit is between 3 and 20")
		private Integer memberLimit;

		@PositiveOrZero(message = "The participation fee cannot be negative. ")
		private Integer fee;

		@Length(min = 4, max = 4, message = "Birth year must be 4 digit number.")
		@Pattern(regexp = "\\d{4}", message = "The birth year must be 4 digit number.")
		private String birthYearMin;

		@Length(min = 4, max = 4, message = "Birth year must be 4 digit number.")
		@Pattern(regexp = "\\d{4}", message = "The birth year must be 4 digit number.")
		private String birthYearMax;

		private Club.GenderRestriction genderRestriction;

		@FutureOrPresent(message = "Appointment date cannot be in the past." )
		private LocalDateTime meetingDate;

		private Boolean isOnline;

		private String location;

		// private String bannerImageUrl;
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
		private CategoryDto.SmallResponse categorySmall;
	}
}
