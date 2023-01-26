package com.codestates.azitserver.domain.follow.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.azitserver.domain.follow.entity.Follow;
import com.codestates.azitserver.domain.member.entity.Member;

public interface FollowRepository extends JpaRepository<Follow, Long> {
	@Query("select f from Follow f where f.follower.memberId = :followerId and f.followee.memberId = :followeeId")
	Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

	Page<Follow> findAllByFollower(Member member, Pageable pageable);
}
