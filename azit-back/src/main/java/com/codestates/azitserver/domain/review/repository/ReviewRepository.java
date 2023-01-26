package com.codestates.azitserver.domain.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	@Query("select r from Review r where r.reviewer = :reviewer and r.reviewee = :reviewee and r.club = :club")
	Optional<Review> findReviewAlreadyWritten(Member reviewer, Member reviewee, Club club);

	@Query("select r from Review r where r.reviewee.memberId = :reieweeId")
	Page<Review> findAllReviewsByRevieweeId(Long reieweeId, PageRequest createdAt);

	@Query("select r from Review r where r.reviewee.memberId = :revieweeId and r.reviewStatus is false")
	Page<Review> findAllReviewsByRevieweeIdWithoutHide(Long revieweeId, PageRequest createdAt);

	@Query("select r from Review r where r.reviewer = :reviewer and r.club = :club")
	List<Review> findAllReviewsByReviewerAndClub(Member reviewer, Club club);
}
