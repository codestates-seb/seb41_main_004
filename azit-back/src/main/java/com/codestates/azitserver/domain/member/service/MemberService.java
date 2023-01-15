package com.codestates.azitserver.domain.member.service;

import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.common.CustomBeanUtils;
import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.repository.MemberRepository;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final CustomBeanUtils<Member> beanUtils;

	//회원 생성
	public Member createMember(Member member) {
		// 닉네임 중복 확인
		verifyExistNickname(member.getNickname());
		// 이메일 중복 확인
		verifyExistEmail(member.getEmail());
		// password 암호화
		String encryptedPassword = passwordEncoder.encode(member.getPassword());
		member.setPassword(encryptedPassword);
		member.setReputation(10);
		member.setMemberStatus(Member.MemberStatus.ACTIVE);

		return memberRepository.save(member);
	}

	//전체 회원 조회
	public Page<Member> getMembers(int page, int size) {
		return memberRepository.findAll(PageRequest.of(page - 1, size));

	}

	//1명의 회원 조회
	public Member getMemberById(Long memberId) {
		return findExistingMember(memberId);
	}

	//회원 수정
	public Member patchMember(Member member) {

		// 기존멤버 찾아
		Member existingMember = findExistingMember(member.getMemberId());

		// 닉네임 변경하지 않는 경우( = 쓰던 닉네임 그대로 patch 요청 보내는 경우 닉중복첵을 하지않는다)
		if (!existingMember.getNickname().equals(member.getNickname())) {
			// 닉네임 변경하는 경우 닉네임 중복 확인
			verifyExistNickname(member.getNickname());
		}

		// 업데이트 해
		Member updatedMember = beanUtils.copyNonNullProperties(member, existingMember);

		// password 암호화
		String encryptedPassword = passwordEncoder.encode(updatedMember.getPassword());
		updatedMember.setPassword(encryptedPassword);

		return memberRepository.save(updatedMember);

	}

	// 회원 삭제(탈퇴)
	public Member deleteMember(Long memberId) {
		Member member = findExistingMember(memberId);
		member.setMemberStatus(Member.MemberStatus.DELETED);
		return memberRepository.save(member);
	}

	//팔로우, 언팔로우
	public Member followMember(Member member) {
		return null; //TODO
	}

	//회원 신고
	public Member reportMember(Member member) {
		return null; //TODO
	}

	//회원 차단
	public Member blockMember(Member member) {
		return null; //TODO
	}

	// 닉네임 중복 확인
	public void verifyExistNickname(String nickname) {
		Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
		if (optionalMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.NICKNAME_EXIST);
		}
	}

	public void verifyExistEmail(String email) {
		Optional<Member> optionalMember = memberRepository.findByEmail(email);
		if (optionalMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST);
		}
	}

	// 'password 한번 더' 절차(post)
	public void passwordConfirmer(MemberDto.Post memberPostDto) {
		if (!Objects.equals(memberPostDto.getPassword(), memberPostDto.getPasswordCheck())) {
			throw new BusinessLogicException(ExceptionCode.PASSWORD_VALIDATION_FAILED);
		}
	}

	// 'password 한번 더' 절차(patch)
	public void passwordConfirmer(MemberDto.Patch memberPatchDto) {
		if (!Objects.equals(memberPatchDto.getPassword(), memberPatchDto.getPasswordCheck())) {
			throw new BusinessLogicException(ExceptionCode.PASSWORD_VALIDATION_FAILED);
		}
	}

	public Member findExistingMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
	}

}
