package com.codestates.azitserver.domain.member.repository;


import com.codestates.azitserver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
