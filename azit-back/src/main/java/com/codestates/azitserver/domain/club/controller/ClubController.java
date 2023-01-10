package com.codestates.azitserver.domain.club.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
		@RequestPart(name = "image", required=false) MultipartFile avatarImage) {
		Club toClub = mapper.clubDtoPostToClubEntity(post);
		Club club = clubService.createClub(toClub, avatarImage);
		ClubDto.Response response = mapper.clubToClubDtoResponse(club);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);

	}
}