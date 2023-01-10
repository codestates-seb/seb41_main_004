package com.codestates.azitserver.domain.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.azitserver.domain.club.entity.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
