package com.codestates.azitserver.domain.member.service;

import com.codestates.azitserver.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    //회원 생성
    public Member createMember(Member member) {

        Member createdMember = member;

        return createdMember; //TODO
    }

    //전체 회원 조회
    public Member getMembers(Member member) {
        return null; //TODO
    }

    //1명의 회원 조회
    public Member getMember(Member member) {
        return null; //TODO
    }

    //회원 수정
    public Member  patchMember(Member member) {
        return null; //TODO
    }

    // 회원 삭제(탈퇴)
    public Member deleteMember(Member member) {
        return null; //TODO
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
}
