package com.codestates.azitserver.domain.club.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	public Club updateClub(Club toClub) {
		Club club = findClubById(toClub.getClubId());

		// TODO : 수정제한 항목에 대한 조건 검사를 해야합니다.

		beanUtils.copyNonNullProperties(toClub, club);

		return clubRepository.save(club);
	}

	public Club updateClubImage(Long clubId, MultipartFile bannerImage) {

		// TODO : banner image에 대한 처리 로직이 필요합니다.

		return null;
	}

	public Club cancelClub(Long clubId) {
		Club club = findClubById(clubId);

		club.setClubStatus(Club.ClubStatus.CLUB_CANCEL);

		return clubRepository.save(club);
	}

	public Page<Club> findClubs(int page, int size) {
		return clubRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
	}

	public Club findClubById(Long clubId) {
		Optional<Club> optionalClub = clubRepository.findById(clubId);
		Club club = optionalClub.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CLUB_NOT_FOUND));
		return club;
	}

	public Page<Club> findClubsByCategoryLargeId(Long categoryLargeId, int page, int size) {
		return clubRepository.findAllClubByCategoryLargeId(categoryLargeId,
			PageRequest.of(page, size, Sort.by("createdAt").descending()));
	}

	public Page<Club> findClubsByClubMeetingDate(int days, int page, int size) {
		// 2023-01-12T00:00:00 ~ 2023-01-13T00:00:00 사이의 모든 아지트를 조회합니다.
		// hibernate jpa는 날짜 계산시 LocalDateTime을 인자로 받습니다.
		LocalDateTime targetDateBefore = LocalDate.now().plusDays(days).atStartOfDay();
		LocalDateTime targetDate = LocalDate.now().plusDays(days + 1).atStartOfDay();

		return clubRepository.findAllClubsByClubMeetingDate(targetDateBefore, targetDate,
			PageRequest.of(page, size, Sort.by("createdAt").descending()));
	}

	public Page<Club> findClubsMemberRecommend(Long memberId, int page, int size) {
		// TODO : Member 도메인이 완성되면 연관관계 매핑 후 회원의 관심 카테고리를 가져와서 db 질의.
		// TODO : 정렬 기준을 어떻게 가져오면 좋을지 고민해보기.

		return null;
	}

	// TODO : MemberId가 참여하고 있는 아지트를 조회하는 서비스 로직 필요.

	public Page<Club> findClubsByKeywords(String keyword, int page, int size) {
		return clubRepository.findAllClubsByNameOrInfoLikeKeywords(keyword.trim(),
			PageRequest.of(page, size, Sort.by("createdAt").descending()));
	}
}
