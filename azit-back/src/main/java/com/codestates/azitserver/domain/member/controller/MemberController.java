package com.codestates.azitserver.domain.member.controller;

import java.util.Comparator;
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

import com.codestates.azitserver.domain.club.dto.ClubMemberDto;
import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.club.mapper.ClubMemberMapper;
import com.codestates.azitserver.domain.club.repository.ClubMemberRepository;
import com.codestates.azitserver.domain.club.repository.ClubRepository;
import com.codestates.azitserver.domain.club.service.ClubMemberService;
import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.mapper.MemberMapper;
import com.codestates.azitserver.domain.member.service.MemberCategoryService;
import com.codestates.azitserver.domain.member.service.MemberService;
import com.codestates.azitserver.global.dto.SingleResponseDto;

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

	private final ClubRepository clubRepository;

	private final ClubMemberRepository clubMemberRepository;
	public MemberController(MemberService memberService, MemberMapper memberMapper,
		MemberCategoryService memberCategoryService, ClubMemberService clubMemberService,
		ClubMemberMapper clubMemberMapper, ClubMemberRepository clubMemberRepository,
		ClubRepository clubRepository) {
		this.memberService = memberService;
		this.memberMapper = memberMapper;
		this.memberCategoryService = memberCategoryService;
		this.clubMemberService = clubMemberService;
		this.clubMemberMapper = clubMemberMapper;
		this.clubMemberRepository = clubMemberRepository;
		this.clubRepository = clubRepository;
	}

	//?????? ??????
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> postMember(@RequestPart(name = "data") @Valid MemberDto.Post memberPostDto,
		@RequestPart(name = "image", required = false) MultipartFile profileImage) {

		Member tempMember = memberMapper.memberPostDtoToMember(memberPostDto);
		List<Long> categorySmallIdList = memberPostDto.getCategorySmallId();

		// 'password ?????? ???' ??????
		memberService.passwordConfirmer(memberPostDto);

		Member createdMember = memberService.createMember(tempMember, profileImage, categorySmallIdList);
		MemberDto.Response response = memberMapper.memberToMemberResponseDto(createdMember);

		// patch??? post??? requestBody??? categorySmallId??? ???????????? memberCategory?????? ?????? ????????? ??????
		response.setCategorySmallIdList(categorySmallIdList);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

	// ?????? ????????? ????????? ??????
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

	//?????? ?????? ??????
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

	//?????? ?????? ??????
	@GetMapping("/{member-id}")
	public ResponseEntity getMember(@Positive @PathVariable("member-id") long memberId) {

		Member member = memberService.getMemberById(memberId);
		MemberDto.Response response = memberMapper.memberToMemberResponseDto(member);

		// ???????????? ??????
		List<Long> foundCategorySmallIdList = memberCategoryService
			.memberCategoryListToCategorySmallIdListByMemberId(memberId);

		response.setCategorySmallIdList(foundCategorySmallIdList);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	// ????????? ?????? ??????
	@PostMapping("/check")
	public ResponseEntity nicknameCheck(@RequestBody MemberDto.CheckPost checkPost) {

		String nickname = checkPost.getNickname();
		String email = checkPost.getEmail();
		memberService.justVerifyExistNicknameAndEmail(nickname, email);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	//?????? ??????
	@PatchMapping("/{member-id}")
	public ResponseEntity patchMember(@Positive @PathVariable("member-id") Long memberId,
		@RequestBody @Valid MemberDto.Patch memberPatchDto) {
		Member member = memberMapper.memberPatchDtoToMember(memberPatchDto);
		List<Long> categorySmallIdList = memberPatchDto.getCategorySmallId();
		member.setMemberId(memberId);

		Member updatedMember = memberService.patchMember(member, categorySmallIdList);
		MemberDto.Response response = memberMapper.memberToMemberResponseDto(member);

		// patch??? post??? requestBody??? categorySmallId??? ???????????? memberCategory?????? ?????? ????????? ??????
		response.setCategorySmallIdList(categorySmallIdList);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	//?????? ??????(??????)
	@DeleteMapping("/{member-id}")
	public ResponseEntity deleteMember(@Positive @PathVariable("member-id") Long memberId) {
		Member member = memberService.deleteMember(memberId);
		MemberDto.Response response = memberMapper.memberToMemberResponseDto(member);
		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.ACCEPTED);
	}

	//?????????????????? ????????????
	@GetMapping("/{member-id}/clubs")
	public ResponseEntity getAttendedClub(@Positive @PathVariable("member-id") Long memberId) {
		Member member = memberService.getMemberById(memberId);
		List<ClubMember> clubMemberList = clubMemberService.getAllClubMemberByMemberId(memberId);
		List<ClubMemberDto.ClubMemberStatusResponse> responses =
			memberService.responseWithInfoGenerator(clubMemberList);


		return new ResponseEntity<>(memberService.responseSorter(responses), HttpStatus.OK);
	}
	// ??????????????? ??????

	/** TODO ???????????? ????????? ??????
	 // my-details-index 0 : CLUB_WAITING
	 // my-details-index 1 : CLUB_JOINED
	 // my-details-index 2 : ????????? ?????????
	 // my-details-index 3 : CLUB_REJECTED (?????????)
	 // my-details-index 4 : CLUB_KICKED (?????????)
	 **/
	@GetMapping("/{member-id}/clubs/{my-details-index}")
	public ResponseEntity getAttendedClubByStatus(@Positive @PathVariable("member-id") Long memberId,
		@PathVariable("my-details-index") int myDetailsIndex) {

		Member member = memberService.getMemberById(memberId);
		ClubMember.ClubMemberStatus status = memberService.numberToStatus(myDetailsIndex);

		List<ClubMember> filteredClubMemberList =
			clubMemberService.getAllClubMemberByMemberIdAndMyDetailsIndex(memberId, myDetailsIndex);
		List<ClubMemberDto.ClubMemberStatusResponse> responses =
			memberService.responseWithInfoGenerator(filteredClubMemberList);

		return new ResponseEntity<>(memberService.responseSorter(responses), HttpStatus.OK);
	}

	//TODO ?????????, ????????????
	@PostMapping("/follows/{member-id}")
	public ResponseEntity followMember() {
		return ResponseEntity.created(null).build();
	}

	//TODO ?????? ??????
	@PostMapping("/blocks/{member-id}")
	public ResponseEntity blockMember() {
		return ResponseEntity.created(null).build();
	}
}
