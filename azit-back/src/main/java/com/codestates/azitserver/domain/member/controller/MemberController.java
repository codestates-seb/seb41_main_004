package com.codestates.azitserver.domain.member.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

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

import com.codestates.azitserver.domain.category.entity.CategorySmall;
import com.codestates.azitserver.domain.club.dto.ClubMemberDto;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.club.mapper.ClubMemberMapper;
import com.codestates.azitserver.domain.club.service.ClubMemberService;
import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.entity.MemberCategory;
import com.codestates.azitserver.domain.member.mapper.MemberMapper;
import com.codestates.azitserver.domain.member.service.MemberCategoryService;
import com.codestates.azitserver.domain.member.service.MemberService;
import com.codestates.azitserver.global.dto.SingleResponseDto;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

@RestController
@RequestMapping(value = "/api/members", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class MemberController {
	private static final String MEMBER_DEFAULT_URL = "/api/members";
	private final MemberService memberService;
	private final MemberMapper memberMapper;
	private final MemberCategoryService memberCategoryService;
	private final ClubMemberService clubMemberService;
	private final ClubMemberMapper clubMemberMapper;

	public MemberController(MemberService memberService, MemberMapper memberMapper,
		MemberCategoryService memberCategoryService, ClubMemberService clubMemberService,
		ClubMemberMapper clubMemberMapper) {
		this.memberService = memberService;
		this.memberMapper = memberMapper;
		this.memberCategoryService = memberCategoryService;
		this.clubMemberService = clubMemberService;
		this.clubMemberMapper = clubMemberMapper;
	}

	//회원 생성
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> postMember(@RequestPart(name = "data") @Valid MemberDto.Post memberPostDto,
		@RequestPart(name = "image", required = false) MultipartFile profileImage) {

		Member tempMember = memberMapper.memberPostDtoToMember(memberPostDto);
		List<Long> categorySmallIdList = memberPostDto.getCategorySmallId();

		// 'password 한번 더' 절차
		memberService.passwordConfirmer(memberPostDto);

		Member createdMember = memberService.createMember(tempMember, profileImage, categorySmallIdList);
		MemberDto.Response response = memberMapper.memberToMemberResponseDto(createdMember);

		// patch와 post는 requestBody에 categorySmallId를 받으므로 memberCategory에서 찾을 필요가 없다
		response.setCategorySmallIdList(categorySmallIdList);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

	// 회원 프로필 이미지 수정
	@PostMapping(value = "/{member-id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> postMemberImage(@Positive @PathVariable("member-id") Long memberId,
		@RequestPart(name = "image", required = false) MultipartFile profileImage) {

		Member member = memberService.updateMemberImage(memberId, profileImage);

		MemberDto.Response response = memberMapper.memberToMemberResponseDto(member);

		List<Long> foundCategorySmallIdList = memberCategoryService
			.memberCategoryListToCategorySmallIdListByMemberId(memberId);

		response.setCategorySmallIdList(foundCategorySmallIdList);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	//전체 회원 조회
	@GetMapping
	public ResponseEntity getMembers(@RequestParam(value = "page", defaultValue = "1") @Positive int page,
		@RequestParam(value = "size", defaultValue = "10") @Positive int size) {

		List<Member> members = memberService.getMembers(page, size).getContent();

		List<MemberDto.Response> memberDtoList = memberMapper.membersToMemberResponseDtos(members);

		for (MemberDto.Response response : memberDtoList) {
			List<Long> foundCategorySmallIdList = memberCategoryService
				.memberCategoryListToCategorySmallIdListByMemberId(response.getMemberId());
			response.setCategorySmallIdList(foundCategorySmallIdList);
		}

		return new ResponseEntity<>(memberDtoList, HttpStatus.OK);
	}

	//개별 회원 조회
	@GetMapping("/{member-id}")
	public ResponseEntity getMember(@Positive @PathVariable("member-id") long memberId) {

		Member member = memberService.getMemberById(memberId);
		MemberDto.Response response = memberMapper.memberToMemberResponseDto(member);

		// 카테고리 찾기
		List<Long> foundCategorySmallIdList = memberCategoryService
			.memberCategoryListToCategorySmallIdListByMemberId(memberId);

		response.setCategorySmallIdList(foundCategorySmallIdList);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	// 닉네임 중복 확인
	@GetMapping("/nickname")
	public ResponseEntity nicknameCheck(@RequestBody MemberDto.NicknameCheck memberNickCheckDto) {


		memberService.verifyExistNickname(memberNickCheckDto.getNickname());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/email")
	public ResponseEntity emailCheck(@RequestBody MemberDto.EmailCheck memberEmailCheckDto) {
		memberService.verifyExistEmail(memberEmailCheckDto.getEmail());


		return new ResponseEntity<>(HttpStatus.OK);
	}

	//회원 수정
	@PatchMapping("/{member-id}")
	public ResponseEntity patchMember(@Positive @PathVariable("member-id") Long memberId,
		@RequestBody @Valid MemberDto.Patch memberPatchDto) {
		Member member = memberMapper.memberPatchDtoToMember(memberPatchDto);
		List<Long> categorySmallIdList = memberPatchDto.getCategorySmallId();
		member.setMemberId(memberId);

		Member updatedMember = memberService.patchMember(member, categorySmallIdList);
		MemberDto.Response response = memberMapper.memberToMemberResponseDto(member);

		// patch와 post는 requestBody에 categorySmallId를 받으므로 memberCategory에서 찾을 필요가 없다
		response.setCategorySmallIdList(categorySmallIdList);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	//회원 삭제(탈퇴)
	@DeleteMapping("/{member-id}")
	public ResponseEntity deleteMember(@Positive @PathVariable("member-id") Long memberId) {
		Member member = memberService.deleteMember(memberId);
		MemberDto.Response response = memberMapper.memberToMemberResponseDto(member);
		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.ACCEPTED);
	}


	//참여상태무관 전체조회
	@GetMapping("/{member-id}/clubs/participation")
	public ResponseEntity clubMemberStatus(@Positive @PathVariable("member-id") Long memberId) {
		Member member = memberService.getMemberById(memberId);
		List<ClubMember> clubMemberList = clubMemberService.getAllClubMemberByMemberId(memberId);
		List<ClubMemberDto.ClubMemberStatusResponse> responses =
			memberService.responseWithInfoGenerator(clubMemberList);

		return new ResponseEntity<>(responses, HttpStatus.OK);
	}
	// 참여상태별 조회
	/** TODO 참여상태 넘버링 변경
	// club-member-status 0 : CLUB_WAITING
	// club-member-status 1 : CLUB_JOINED
	// club-member-status 2 : 종료된 아지트
	// club-member-status 3 : CLUB_REJECTED (미사용)
	// club-member-status 4 : CLUB_KICKED (미사용)
	**/
	@GetMapping("/{member-id}/clubs/{club-member-status}")
	public ResponseEntity clubMemberStatusWaiting(@Positive @PathVariable("member-id") Long memberId,
		@PathVariable("club-member-status") int myDetailsIndex) {

		Member member = memberService.getMemberById(memberId);
		ClubMember.ClubMemberStatus status = memberService.numberToStatus(myDetailsIndex);

		List<ClubMember> filteredClubMemberList =
			clubMemberService.getAllClubMemberByMemberIdAndMyDetailsIndex(memberId, myDetailsIndex);
		List<ClubMemberDto.ClubMemberStatusResponse> responses =
			memberService.responseWithInfoGenerator(filteredClubMemberList);

		return new ResponseEntity<>(responses, HttpStatus.OK);
	}

	//TODO 팔로우, 언팔로우
	@PostMapping("/follows/{member-id}")
	public ResponseEntity followMember() {
		return ResponseEntity.created(null).build();
	}

	//TODO 회원 차단
	@PostMapping("/blocks/{member-id}")
	public ResponseEntity blockMember() {
		return ResponseEntity.created(null).build();
	}
}
