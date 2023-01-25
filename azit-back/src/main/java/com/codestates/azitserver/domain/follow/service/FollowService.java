package com.codestates.azitserver.domain.follow.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.follow.entity.Follow;
import com.codestates.azitserver.domain.follow.repository.FollowRepository;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.service.MemberService;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {
	private final FollowRepository followRepository;
	private final MemberService memberService;

	/**
	 * 팔로우 신청을 하는 서비스 로직입니다.
	 * @param member 팔로우 신청을하는 회원
	 * @param memberId 팔로우 하려는 회원의 고유 식별자
	 * @return 팔로우 신청이 성공하면 팔로우 정보를 리턴합니다.
	 * @author cryoon
	 */
	public Follow followMember(Member member, Long memberId) {
		// 팔로워, 팔로잉이 존재하는지 확인합니다.
		Member follower = memberService.findExistingMember(member.getMemberId());
		Member followee = memberService.findExistingMember(memberId);

		// 이미 팔로우한 상태인지 확인합니다.
		if (isExistFollowByIds(member.getMemberId(), memberId)) {
			throw new BusinessLogicException(ExceptionCode.FOLLOW_EXIST);
		}

		// 팔로우 정보를 생성합니다.
		Follow.Builder builder = new Follow.Builder(follower, followee);
		Follow follow = builder.build();

		return followRepository.save(follow);
	}

	//*** verfification ***//
	/**
	 * 로그인한 회원과 요청하려는 회원이 일치하는지 확인합니다.
	 * @param member 로그인 회원
	 * @param memberId 요청 대상의 회원
	 * @return 로그인한 회원과 요청회원의 일치여부(true: 일치함, false: 일치하지 않음)
	 * @author cryoon
	 */
	public boolean verifyMemberAndMemberId(Member member, Long memberId) {
		return member.getMemberId().equals(memberId);
	}

	/**
	 * 팔로워 id, 팔로잉 id를 통해 팔로우를 했는지 여부를 확인합니다.
	 * @param followerId 팔로우하는 회원의 고유 식별자
	 * @param followeeId 팔로잉하는 회원의 고유 식별자
	 * @return 팔로우를 했는지 여부를 반환하는 boolean값(treu: 팔로우중, false: 팔로우중 아님)
	 * @author cryoon
	 */
	public boolean isExistFollowByIds(Long followerId, Long followeeId) {
		Optional<Follow> optionalFollow = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId);

		return optionalFollow.isPresent();
	}
}
