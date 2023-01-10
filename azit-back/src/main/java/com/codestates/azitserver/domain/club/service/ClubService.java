package com.codestates.azitserver.domain.club.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.repository.ClubRepository;
import com.codestates.azitserver.domain.common.CustomBeanUtils;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubService {
	private final ClubRepository clubRepository;
	private final CustomBeanUtils<Club> beanUtils;

	public Club createClub(Club toClub, MultipartFile bannerImage) {
		// 온라인 여부에 따른 null 또는 주소값 저장
		toClub.setLocation(toClub.getIsOnline() ? null : toClub.getLocation());

		// TODO : banner image에 대한 처리 로직이 필요합니다.

		return clubRepository.save(toClub);
	}

	/**
	 * - 수정 제한 항목(신청자 또는 참가자 있을시) : clubName, memberLimit, meetingDate, isOnline,location
	 */
	public Club updateClub(Club toClub, MultipartFile bannerImage) {
		Club club = findClubById(toClub.getClubId());

		// TODO : 수정제한 항목에 대한 조건 검사를 해야합니다.

		// TODO : banner image에 대한 처리 로직이 필요합니다.

		beanUtils.copyNonNullProperties(toClub, club);

		return clubRepository.save(club);
	}

	public Club cancelClub(Long clubId) {
		Club club = findClubById(clubId);

		club.setClubStatus(Club.ClubStatus.CLUB_CANCEL);

		return clubRepository.save(club);
	}

	public Club findClubById(Long clubId) {
		Optional<Club> optionalClub = clubRepository.findById(clubId);
		Club club = optionalClub.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CLUB_NOT_FOUND));

		return club;
	}
}
