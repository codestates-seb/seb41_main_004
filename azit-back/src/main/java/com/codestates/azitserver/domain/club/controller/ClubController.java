package com.codestates.azitserver.domain.club.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codestates.azitserver.domain.club.dto.ClubDto;
import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.mapper.ClubMapper;
import com.codestates.azitserver.domain.club.service.ClubService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.global.annotation.LoginMember;
import com.codestates.azitserver.global.dto.MultiResponseDto;
import com.codestates.azitserver.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;

/**
 * TODO : Login 피트가 구현 되면 Member Principal을 받아와 요청의 주체와, 요청의 대상을 검증하는 로직 추가.
 * TODO : Member 파트가 구현 되면 관심 카테고리, 회원이 참여한 정보를 가져와 조회 api 완성.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class ClubController {
	private final ClubMapper mapper;
	private final ClubService clubService;

	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> postClub(@Valid @RequestPart(name = "data") ClubDto.Post post,
		@RequestPart(name = "image", required = false) MultipartFile bannerImage,
		@LoginMember Member member) {
		Club toClub = mapper.clubDtoPostToClubEntity(post);
		toClub.setHost(member);

		Club club = clubService.createClub(toClub, bannerImage);
		ClubDto.Response response = mapper.clubToClubDtoResponse(club);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);

	}

	/**
	 * 아지트 배너 이미지 변경
	 * @param clubId 아지트 고유 식별자
	 * @param bannerImage 바꾸려는 이미지 정보
	 * @return 업데이트 된 아지트 정보를 리턴합니다.
	 */
	@PostMapping(value = "/{club-id:[0-9]+}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> postClubImage(@Positive @PathVariable("club-id") Long clubId,
		@RequestPart(name = "image", required = false) MultipartFile bannerImage) {
		Club club = clubService.updateClubImage(clubId, bannerImage);
		ClubDto.Response response = mapper.clubToClubDtoResponse(club);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	@PatchMapping("/{club-id:[0-9]+}")
	public ResponseEntity<?> patchClub(@Positive @PathVariable("club-id") Long clubId,
		@Valid @RequestBody ClubDto.Patch patch) {
		patch.setClubId(clubId);
		Club toClub = mapper.clubDtoPatchToClubEntity(patch);
		Club club = clubService.updateClub(toClub);
		ClubDto.Response response = mapper.clubToClubDtoResponse(club);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	@DeleteMapping("/{club-id:[0-9]+}")
	public ResponseEntity<?> deleteClub(@Positive @PathVariable("club-id") Long clubId) {
		Club club = clubService.cancelClub(clubId);
		ClubDto.Response response = mapper.clubToClubDtoResponse(club);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.ACCEPTED);
	}

	@GetMapping
	public ResponseEntity<?> getAllClub(@Positive @RequestParam(name = "page") int page,
		@Positive @RequestParam(name = "size") int size) {
		Page<Club> clubPage = clubService.findClubs(page - 1, size);
		List<Club> clubs = clubPage.getContent();
		List<ClubDto.Response> response = mapper.clubToClubDtoResponse(clubs);

		return new ResponseEntity<>(new MultiResponseDto<>(response, clubPage), HttpStatus.OK);
	}

	/**
	 * 아지트 상세 정보 조회
	 * @param clubId 아지트 고유 식별자
	 * @return clubId로 조회한 아지트에 정보를 리턴합니다.
	 */
	@GetMapping("/{club-id:[0-9]+}")
	public ResponseEntity<?> getClubByClubId(@Positive @PathVariable("club-id") Long clubId) {
		Club club = clubService.findClubById(clubId);
		ClubDto.Response response = mapper.clubToClubDtoResponse(club);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	/**
	 * 카테고리별 아지트 리스트 조회
	 * @param page 페이지 번호
	 * @param size 페이지 내 보여줄 아지트 개수
	 * @param categoryLargeId 대분류 카테고리 고유 식별자
	 * @return categoryLargeId 조회한 아지트에 정보를 리턴합니다.
	 */
	@GetMapping("/category")
	public ResponseEntity<?> getClubByCategoryId(@Positive @RequestParam(name = "page") int page,
		@Positive @RequestParam(name = "size") int size, @Positive @RequestParam(name = "cl") Long categoryLargeId) {
		Page<Club> clubPage = clubService.findClubsByCategoryLargeId(categoryLargeId, page - 1, size);
		List<Club> clubs = clubPage.getContent();
		List<ClubDto.Response> response = mapper.clubToClubDtoResponse(clubs);

		return new ResponseEntity<>(new MultiResponseDto<>(response, clubPage), HttpStatus.OK);
	}

	/**
	 * 날짜별 아지트 리스트 조회
	 * @param page 페이지 번호
	 * @param size 페이지 내 보여줄 아지트 개수
	 * @param days 오늘 기준으로 며칠 뒤의 아지트를 조회할 것인지(0 ~ 5)
	 * @return date 기준으로 조회한 아지트의 정보를 리턴합니다.
	 */
	@GetMapping("/date")
	public ResponseEntity<?> getClubByDate(@Positive @RequestParam(name = "page") int page,
		@Positive @RequestParam(name = "size") int size, @PositiveOrZero @RequestParam(name = "days") int days) {
		Page<Club> clubPage = clubService.findClubsByClubMeetingDate(days, page - 1, size);
		List<Club> clubs = clubPage.getContent();
		List<ClubDto.Response> response = mapper.clubToClubDtoResponse(clubs);

		return new ResponseEntity<>(new MultiResponseDto<>(response, clubPage), HttpStatus.OK);
	}

	/**
	 * 회원 괸심 카테고리 기준 아지트 조회
	 * @param page 페이지 번호
	 * @param size 페이지 내 보여줄 아지트 개수
	 * @param memberId 회원 고유 식별자
	 * @return memberId에서 가져온 회원의 관심 카테고리를 기준으로 조회한 아지트의 정보를 리턴합니다.
	 */
	@GetMapping("/recommend/{member-id:[0-9]+}")
	public ResponseEntity<?> getClubByMemberRecommend(@Positive @RequestParam(name = "page") int page,
		@Positive @RequestParam(name = "size") int size, @Positive @PathVariable("member-id") Long memberId) {

		// TODO : Need to service logic.

		Page<Club> clubPage = clubService.findClubsMemberRecommend(memberId, page - 1, size);
		List<Club> clubs = clubPage.getContent();
		List<ClubDto.Response> responses = mapper.clubToClubDtoResponse(clubs);

		return new ResponseEntity<>(new MultiResponseDto<>(responses, clubPage), HttpStatus.OK);
	}

	/**
	 * 특정 회원이 참여한 아지트 조회
	 * @param page 페이지 번호
	 * @param size 페이지 내 보여줄 아지트 개수
	 * @param memberId 회원 고유 식별자
	 * @return ClubMember에서 memberId가 있는 아지트의 정보를 리턴합니다.
	 */
	// /members/{member-id}?page=1&size=10
	@GetMapping("/members/{member-id:[0-9]+}")
	public ResponseEntity<?> getClubByMemberId(@Positive @RequestParam(name = "page") int page,
		@Positive @RequestParam(name = "size") int size, @Positive @PathVariable("member-id") Long memberId) {

		// TODO : implement the code.

		return null;
	}

	/**
	 * 키워드 검색 : 제목 또는 내용에 키워드가 포함된 아지트를 찾습니다.
	 * @param page 페이지 번호
	 * @param size 페이지 내 보여줄 아지트 개수
	 * @param keyword 검색 키워드
	 * @return 제목에 키워드가 포함된 아지트의 정보를 리턴합니다.
	 */
	@GetMapping("/search")
	public ResponseEntity<?> search(@Positive @RequestParam(name = "page") int page,
		@Positive @RequestParam(name = "size") int size, @NotNull @RequestParam(name = "keyword") String keyword) {
		// 검색 키워드가 빈칸이면 모든 아지트를 리턴합니다.
		if (keyword.equals("")) {
			return getAllClub(page, size);
		}
		Page<Club> clubPage = clubService.findClubsByKeywords(keyword, page - 1, size);
		List<Club> clubs = clubPage.getContent();
		List<ClubDto.Response> responses = mapper.clubToClubDtoResponse(clubs);

		return new ResponseEntity<>(new MultiResponseDto<>(responses, clubPage), HttpStatus.OK);
	}
}