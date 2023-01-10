package com.codestates.azitserver.domain.club.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.repository.ClubRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubService {
	private final ClubRepository clubRepository;

	public Club createClub(Club toClub, MultipartFile avatarImage) {
		// 온라인 여부에 따른 null 또는 주소값 저장
		toClub.setLocation(toClub.getIsOnline() ? null : toClub.getLocation());

		// TODO : avatar image 처리 로직 필요

		return clubRepository.save(toClub);
	}
}
