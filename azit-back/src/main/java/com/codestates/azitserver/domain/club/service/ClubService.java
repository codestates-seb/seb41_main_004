package com.codestates.azitserver.domain.club.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.repository.ClubRepository;
import com.codestates.azitserver.domain.common.CustomBeanUtils;
import com.codestates.azitserver.domain.fileInfo.entity.FileInfo;
import com.codestates.azitserver.domain.fileInfo.service.StorageService;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubService {
	private final ClubRepository clubRepository;
	private final CustomBeanUtils<Club> beanUtils;
	private final StorageService storageService;

	public Club createClub(Club toClub, MultipartFile bannerImage) {
		// online offline 외 문자열이 들어올 경우 예외처리
		switch (toClub.getIsOnline()) {
			case "offline":
				toClub.setLocation(toClub.getLocation());
				break;
			case "online":
				toClub.setLocation(null);
				break;
			default:
				throw new BusinessLogicException(ExceptionCode.INVALID_MEETING_METHOD);
		}

		// 데이터 저장
		Club club = clubRepository.save(toClub);

		// banner image 저장 후 리턴
		return updateClubImage(club.getClubId(), bannerImage);
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
		Club club = findClubById(clubId);

		// banner image 저장
		String prefix = "images/club_banner";
		Map<String, String> map = storageService.upload(prefix, bannerImage);

		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(map.get("fileName"));
		fileInfo.setFileUrl(map.get("fileUrl"));

		club.setFileInfo(fileInfo);

		return clubRepository.save(club);
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
		LocalDate targetDate = LocalDate.now().plusDays(days);

		return clubRepository.findAllClubsByClubMeetingDate(targetDate,
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

	public void verifyClubCanceled(Club club) {
		if (club.getClubStatus() == Club.ClubStatus.CLUB_CANCEL) {
			throw new BusinessLogicException(ExceptionCode.CLUB_CANCELED);
		}
	}
}
