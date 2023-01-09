package com.codestates.azitserver.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.azitserver.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);

	Optional<Member> findByEmailAndMemberStatus(String email, Member.MemberStatus memberStatus);
}
