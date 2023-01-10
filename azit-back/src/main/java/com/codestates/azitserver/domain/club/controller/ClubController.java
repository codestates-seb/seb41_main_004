package com.codestates.azitserver.domain.club.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codestates.azitserver.domain.club.dto.ClubDto;
import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.mapper.ClubMapper;
import com.codestates.azitserver.domain.club.service.ClubService;
import com.codestates.azitserver.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class ClubController {
	private final ClubMapper mapper;
	private final ClubService clubService;

	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> postClub(@Valid @RequestPart(name = "data") ClubDto.Post post,
		@RequestPart(name = "image", required = false) MultipartFile bannerImage) {
		Club toClub = mapper.clubDtoPostToClubEntity(post);
		Club club = clubService.createClub(toClub, bannerImage);
		ClubDto.Response response = mapper.clubToClubDtoResponse(club);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);

	}

	@PatchMapping("/{club-id}")
	public ResponseEntity<?> patchClub(@Positive @PathVariable("club-id") Long clubId,
		@Valid @RequestPart(name = "data") ClubDto.Patch patch,
		@RequestPart(name = "image", required = false) MultipartFile bannerImage) {
		patch.setClubId(clubId);
		Club toClub = mapper.clubDtoPatchToClubEntity(patch);
		Club club = clubService.updateClub(toClub, bannerImage);
		ClubDto.Response response = mapper.clubToClubDtoResponse(club);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	@DeleteMapping("/{club-id}")
	public ResponseEntity<?> deleteClub(@Positive @PathVariable("club-id") Long clubId) {
		Club club = clubService.cancelClub(clubId);
		ClubDto.Response response = mapper.clubToClubDtoResponse(club);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.ACCEPTED);
	}
}