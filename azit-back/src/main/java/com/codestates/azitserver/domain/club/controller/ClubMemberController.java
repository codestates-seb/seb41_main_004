package com.codestates.azitserver.domain.club.controller;

import java.util.List;

import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.azitserver.domain.club.dto.ClubMemberDto;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.club.mapper.ClubMemberMapper;
import com.codestates.azitserver.domain.club.service.ClubMemberService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.global.annotation.LoginMember;
import com.codestates.azitserver.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/clubs/{club-id:[0-9]+}")
@RequiredArgsConstructor
public class ClubMemberController {
	private final ClubMemberService clubMemberService;
	private final ClubMemberMapper mapper;

	/**
	 * 특정 아지트에 참여 신청을 보내는 컨트롤러입니다.
	 * @param clubId 참여 신청을 보낼 아지트 고유 식별자
	 * @param body 참여 신청을 위해 작성하는 항목
	 * @param member 참여 신청을 하는 현재 로그인한 사용자
	 * @return 참여 신청이 성공하면 참여 신청 정보를 http status화 함께 반환
	 */
	@PostMapping("/signups")
	public ResponseEntity<?> postClubMember(@Positive @PathVariable("club-id") Long clubId,
		@RequestBody ClubMemberDto.Signup body,
		@LoginMember Member member) {
		clubMemberService.verifyMember(member, body.getMemberId());
		ClubMember clubMember = clubMemberService.signup(member, clubId, body.getJoinAnswer());

		ClubMemberDto.Response response = mapper.clubMemberToClubMemberDtoResponse(clubMember);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

	/**
	 * 특정 아지트에 참여신청을 보낸 사용자를 전체 조회합니다.
	 * @param clubId 조회할 아지트 고유 식별자
	 * @return 성공하면 상태값과 함께 참여 신청을 보낸 사용자를 담은 배열을 반환합니다.
	 */
	@GetMapping("/signups")
	public ResponseEntity<?> getClubMember(@Positive @PathVariable("club-id") Long clubId) {
		List<ClubMember> clubMembers = clubMemberService.getAllClubMemberByClubId(clubId);
		List<ClubMemberDto.Response> responses = mapper.clubMemberToClubMemberDtoResponse(clubMembers);

		return new ResponseEntity<>(new SingleResponseDto<>(responses), HttpStatus.OK);
	}

	/**
	 * 호스트가 신청한 사용자를 아지트에 수락하거나 거절합니다.
	 * @param clubId 신청한 사용자가 있는 아지트 고유 식별자
	 * @param memberId 아지트에 신청한 회원 고유 식별자
	 * @param body 신청 상태를 담은 body
	 * @param member 현재 요청을 보낸 로그인 유저
	 * @return 변경 내용을 따로 담지 않고, accepted 됐다는 상태코드만 리턴합니다.
	 */
	@PatchMapping("/signups/{member-id:[0-9]+}")
	public ResponseEntity<?> patchClubMembers(@Positive @PathVariable("club-id") Long clubId,
		@Positive @PathVariable("member-id") Long memberId, @RequestBody ClubMemberDto.Patch body,
		@LoginMember Member member) {
		clubMemberService.updateMemberStatus(member, clubId, memberId,
			ClubMember.ClubMemberStatus.valueOf(body.getStatus()));

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@PatchMapping("/kicks/{member-id:[0-9]+}")
	public ResponseEntity<?> kickClubMembers(@Positive @PathVariable("club-id") Long clubId,
		@Positive @PathVariable("member-id") Long memberId,
		@LoginMember Member member) {
		clubMemberService.kickMember(member, clubId, memberId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/signups/{member-id:[0-9]+}")
	public ResponseEntity<?> deleteClubMembers(@Positive @PathVariable("club-id") Long clubId,
	@Positive @PathVariable("member-id") Long memberId, @LoginMember Member member) {
		clubMemberService.deleteClubMember(member, clubId, memberId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
