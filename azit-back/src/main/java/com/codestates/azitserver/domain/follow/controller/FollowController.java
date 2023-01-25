package com.codestates.azitserver.domain.follow.controller;

import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.azitserver.domain.follow.dto.FollowDto;
import com.codestates.azitserver.domain.follow.entity.Follow;
import com.codestates.azitserver.domain.follow.mapper.FollowMapper;
import com.codestates.azitserver.domain.follow.service.FollowService;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.global.annotation.LoginMember;
import com.codestates.azitserver.global.dto.SingleResponseDto;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/members/{member-id:[0-9]+}")
@RequiredArgsConstructor
public class FollowController {
	private final FollowService followService;
	private final FollowMapper mapper;

	/**
	 * 해당 회원을 팔로우 합니다.
	 * @param memberId 팔로우 할 회원 고유 식별자
	 * @param member 팔로우를 신청하는 회원
	 * @return 팔로우 정보를 리턴합니다.
	 * @author cryoon
	 */
	@PostMapping("/follow")
	public ResponseEntity<?> postFollow(@Positive @PathVariable("member-id") Long memberId,
		@LoginMember Member member) {
		if (followService.verifyMemberAndMemberId(member, memberId)) {
			throw new BusinessLogicException(ExceptionCode.INVALID_APPLY_FOLLOW_);
		}

		Follow follow = followService.followMember(member, memberId);
		FollowDto.Response response = mapper.followToFollowDtoResponse(follow);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

	/**
	 * 해당 회원을 언팔로우 합니다.
	 * @param memberId 팔로우 할 회원 고유 식별자
	 * @param member 팔로우를 신청하는 회원
	 * @return 팔로우 정보를 리턴합니다.
	 * @author cryoon
	 */
	@PostMapping("/unfollow")
	public ResponseEntity<?> postUnfollow(@Positive @PathVariable("member-id") Long memberId,
		@LoginMember Member member) {
		return null;
	}

	/**
	 * 나를 팔로우한 사람들의 리스트를 조회합니다.
	 * @param memberId 팔로우한 사람들의 리스트를 볼 회원 고유 식별자
	 * @return 회원을 팔로우한 회원들의 리스트를 반환합니다.
	 * @author cryoon
	 */
	@GetMapping("/follower")
	public ResponseEntity<?> getFollowers(@Positive @PathVariable("member-id") Long memberId) {
		return null;
	}

	/**
	 * 내가 팔로우한 사람들의 리스트를 조회합니다.
	 * @param memberId 내가 팔로우한 사람들을 보기위한 회원 고유 식별자
	 * @return 회원이 팔로우한 회원들의 리스트를 반환합니다.
	 * @author cryoon
	 */
	@GetMapping("/following")
	public ResponseEntity<?> getFollowings(@Positive @PathVariable("member-id") Long memberId) {
		// TODO : 단순한 조회가 아닌 리스트 중에 나와의 팔로우 상태값까지 가져와야 합니다.

		return null;
	}

	/**
	 * 나를 팔로우 한 사람들 중 내가 강제로 팔로우를 해제합니다.
	 * @param memberId 나.
	 * @param followId 나를 팔로우 한 리스트중 해제하고 싶은 팔로우 고유 식별자
	 * @param member 요청의 주체. 로그인한 유저.
	 * @author cryoon
	 */
	@DeleteMapping("/follower/{follow-id:[0-9]+}")
	public void deleteFollowers(@Positive @PathVariable("member-id") Long memberId,
		@Positive @PathVariable("follow-id") Long followId, @LoginMember Member member) {
	}

}
