package com.codestates.azitserver.domain.club.controller;

import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.azitserver.domain.club.dto.ClubReportDto;
import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.entity.ClubReport;
import com.codestates.azitserver.domain.club.mapper.ClubReportMapper;
import com.codestates.azitserver.domain.club.service.ClubReportService;
import com.codestates.azitserver.domain.club.service.ClubService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.global.annotation.LoginMember;
import com.codestates.azitserver.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/clubs/reports")
@RequiredArgsConstructor
public class ClubReportController {
	private final ClubReportService clubReportService;
	private final ClubService clubService;
	private final ClubReportMapper mapper;

	@PostMapping("/{club-id:[0-9]+}")
	public ResponseEntity<?> postClubReport(@Positive @PathVariable("club-id") Long clubId,
		@RequestBody ClubReportDto.Post post, @LoginMember Member member) {
		ClubReport toClubReport = mapper.clubReportDtoPostToClubReport(post);

		Club club = clubService.findClubById(clubId);
		toClubReport.setClub(club);

		ClubReport clubReport = clubReportService.createClubReport(member, clubId, toClubReport);
		ClubReportDto.Response response = mapper.clubReportToClubReportDtoResponse(clubReport);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

}
