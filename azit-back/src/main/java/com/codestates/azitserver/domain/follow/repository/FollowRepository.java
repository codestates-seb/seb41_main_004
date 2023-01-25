package com.codestates.azitserver.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.azitserver.domain.follow.entity.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
