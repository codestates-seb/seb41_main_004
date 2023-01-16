package com.codestates.azitserver.domain.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.azitserver.domain.club.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
}
