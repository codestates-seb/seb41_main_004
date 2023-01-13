package com.codestates.azitserver.domain.member.service;

import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.repository.MemberRepository;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
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
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member member = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return member;
    }

    //회원 수정
    public Member patchMember(Member member) {
        // 닉네임 중복 확인
        verifyExistNickname(member.getNickname());
        // 이메일 중복 확인
        verifyExistEmail(member.getEmail());
        // password 암호화
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        return memberRepository.save(member);

    }

    // 회원 삭제(탈퇴)
    public Member deleteMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member member = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        member.setMemberStatus(Member.MemberStatus.DELETED);
        return member;
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


}
