package com.codestates.azitserver.domain.auth.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.repository.MemberRepository;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public boolean passwordMatcher(Long memberId, String inputPassword) {
		// 입력받은 memberId로 memberRepository 안의 member를 찾는다
		Member findMember = findVerifiedMember(memberId);

		// 찾은 member의 Password와 입력받은 inputPassword가 일치하는지 확인한다.
		// 그 boolean 값을 return해준다.

		return true;
	}

	public Member updatePassword(Long memberId, String inputNewPassword) {
		// TODO : 요청 주체와 memberId가 일치하는지 확인해서 일치하면 이후 과정 진행

		// 입력받은 memberId로 memberRepository 안의 member를 찾는다
		Member findMember = findVerifiedMember(memberId);

		// 찾은 member에 입력받은 inputNewPassword를 암호화해서 넣어주고 레포지터리에 저장
		// google 로그인의 경우 비밀번호가 없으니 비밀번호가 있는 경우에만 수정
		try{
			if (findMember.getPassword() != null) {
				findMember.setPassword(passwordEncoder.encode(inputNewPassword));
				memberRepository.save(findMember);
			}
		} catch (EntityNotFoundException e) {
			log.warn("Failed to change password. Existing password not found: {}", e.getLocalizedMessage());
		}

		return findMember;
	}

	// memberId로 Repository에서 해당하는 멤버 찾아서 정보 반환
	private Member findVerifiedMember(Long memberId) {
		Optional<Member> optionalMember = memberRepository.findById(memberId);
		Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

		return findMember;
	}
}
