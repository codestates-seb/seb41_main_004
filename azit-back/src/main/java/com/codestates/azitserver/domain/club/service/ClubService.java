package com.codestates.azitserver.domain.club.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codestates.azitserver.domain.category.entity.CategorySmall;
import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.club.repository.ClubMemberRepository;
import com.codestates.azitserver.domain.club.repository.ClubRepository;
import com.codestates.azitserver.domain.common.CustomBeanUtils;
import com.codestates.azitserver.domain.fileInfo.entity.FileInfo;
import com.codestates.azitserver.domain.fileInfo.service.StorageService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.entity.MemberCategory;
import com.codestates.azitserver.domain.member.service.MemberService;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubService {
	private final ClubRepository clubRepository;
	private final MemberService memberService;
	private final CustomBeanUtils<Club> beanUtils;
	private final StorageService storageService;
	private final ClubMemberRepository clubMemberRepository;


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

		// Host 정보 클럽에 넣기
		Club createdClub = findClubById(club.getClubId());
			// Member(=host), 방금 생성한 클럽을 필드로 가지는 clubMember 생성
		ClubMember clubMember = new ClubMember(createdClub.getHost(), createdClub, "");
			// 참가 상태는 "JOINED"
		clubMember.setClubMemberStatus(ClubMember.ClubMemberStatus.CLUB_JOINED);
			// 생성한 clubMember를 DB에 저장
		clubMemberRepository.save(clubMember);

		List<ClubMember> clubMemberList = new ArrayList<>();
		clubMemberList.add(clubMember);
			// 저장한 clubMember를 방금 생성한 클럽에도 입력
		createdClub.setClubMembers(clubMemberList);
		// 최종적으로 host를 갖고있는 club을 DB에 저장
		Club updatedClub = clubRepository.save(createdClub);

		// banner image 저장 후 리턴
		return updateClubImage(updatedClub.getClubId(), bannerImage);
	}

	/**
	 * - 수정 제한 항목(신청자 또는 참가자 있을시) : clubName, memberLimit, meetingDate, isOnline,location
	 */
	public Club updateClub(Club toClub) {
		Club club = findClubById(toClub.getClubId());

		// TODO : 수정제한 항목에 대한 조건 검사를 해야합니다.

		// 생년 제한은 제한 없음으로 올 경우 null로 저장합니다.
		// 다음부턴 입력값을 null로 받지 말자ㅠㅠ
		club.setBirthYearMax(toClub.getBirthYearMax());
		club.setBirthYearMin(toClub.getBirthYearMin());

		beanUtils.copyNonNullProperties(toClub, club);

		return clubRepository.save(club);
	}

	public Club updateClubImage(Long clubId, MultipartFile bannerImage) {
		Club club = findClubById(clubId);

		// banner image 저장
		String prefix = "/images/club_banner";
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
		return clubRepository.findAllWithoutCanceled(PageRequest.of(page, size, Sort.by("createdAt").descending()));
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
		// TODO : 정렬 기준을 어떻게 가져오면 좋을지 고민해보기. 현재는 최신순으로 내림차순 정렬

		Member member = memberService.findExistingMember(memberId);
		List<MemberCategory> memberCategoryList = member.getMemberCategoryList();

		List<CategorySmall> categorySmall = memberCategoryList.stream()
			.map(MemberCategory::getCategorySmall)
			.collect(Collectors.toList());

		Page<Club> clubs = new PageImpl<>(List.of(), PageRequest.of(page, size,
			Sort.by("createdAt").descending()), 0);
		if (categorySmall.size() != 0) {
			clubs = clubRepository.findAllClubByCategorySmallIds(categorySmall,
				PageRequest.of(page, size, Sort.by("createdAt").descending()));
		}

		return clubs;
	}

	// TODO : MemberId가 참여하고 있는 아지트를 조회하는 서비스 로직 필요.

	public Page<Club> findClubsByKeywords(String keyword, int page, int size) {
		return clubRepository.findAllClubsByNameOrInfoLikeKeywords(keyword.trim(),
			PageRequest.of(page, size, Sort.by("createdAt").descending()));
	}

	public void verifyMemberIsClubHost(Member member, Long clubId) {
		Club club = findClubById(clubId);

		if (!member.getMemberId().equals(club.getHost().getMemberId())) {
			throw new BusinessLogicException(ExceptionCode.HOST_FORBIDDEN);
		}
	}

	public void verifyClubCanceled(Club club) {
		if (club.getClubStatus() == Club.ClubStatus.CLUB_CANCEL) {
			throw new BusinessLogicException(ExceptionCode.CLUB_CANCELED);
		}
	}

	public boolean verifyOrFindAll(Member member, Long memberId) {
		return member == null || !memberId.equals(member.getMemberId());
	}

	public String getClubJoinQuestion(Long clubId) {
		Club club = findClubById(clubId);
		return club.getJoinQuestion();
	}

	public List<ClubMember.ClubMemberStatus> getClubMemberStatusList(Member member) {
		List<ClubMember> clubMemberList = clubMemberRepository.findClubMembersByMember(member);
		List<ClubMember.ClubMemberStatus> clubMemberStatusList = new ArrayList<>();

		for (ClubMember clubMember : clubMemberList) {
			clubMemberStatusList.add(clubMember.getClubMemberStatus());
		}
		return clubMemberStatusList;
	}
}
