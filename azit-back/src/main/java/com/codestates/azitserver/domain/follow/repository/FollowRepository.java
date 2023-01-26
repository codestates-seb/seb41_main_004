package com.codestates.azitserver.domain.follow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.azitserver.domain.follow.entity.Follow;
import com.codestates.azitserver.domain.member.entity.Member;

public interface FollowRepository extends JpaRepository<Follow, Long> {
	@Query("select f from Follow f where f.follower.memberId = :followerId and f.followee.memberId = :followeeId")
	Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

	Page<Follow> findAllByFollowee(Member member, PageRequest pageable);

	// SELECT F1.FOLLOW_ID, F1.FOLLOWER_ID, F1.FOLLOWEE_ID, F1.CREATED_AT,
	// (SELECT TRUE FROM FOLLOW F2 WHERE F1.FOLLOWER_ID = F2.FOLLOWEE_ID AND F1.FOLLOWEE_ID=F2.FOLLOWER_ID) "MATPAL"
	// FROM FOLLOW F1
	// WHERE FOLLOWER_ID = 1;
	@Query("select new Follow (f1.followId, f1.follower, f1.followee, "
		+ "(select true from Follow f2 where f1.follower = f2.followee and f1.followee = f2.follower)) "
		+ "from Follow f1 where f1.follower = :member ")
	List<Follow> findAllByFollower(Member member);
}
