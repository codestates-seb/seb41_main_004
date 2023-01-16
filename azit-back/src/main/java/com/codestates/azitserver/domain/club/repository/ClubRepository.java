package com.codestates.azitserver.domain.club.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.azitserver.domain.club.entity.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
	// SELECT * FROM CLUB INNER JOIN CATEGORY_SMALL WHERE CATEGORY_SMALL.CATEGORY_LARGE_ID = 1 AND CATEGORY_SMALL.CATEGORY_SMALL_ID = CLUB.CATEGORY_SMALL_ID
	@Query("select c from Club c where c.categorySmall.categoryLarge.CategoryLargeId = :categoryLargeId")
	Page<Club> findAllClubByCategoryLargeId(Long categoryLargeId, Pageable pageable);

	// SELECT * FROM CLUB WHERE MEETING_DATE = {날짜}
	@Query("select c from Club c where c.meetingDate = :targetDate")
	Page<Club> findAllClubsByClubMeetingDate(LocalDate targetDate, PageRequest createdAt);

	// SELECT * FROM CLUB WHERE CLUB_NAME LIKE '%{keyword}%' OR CLUB_INFO LIKE '%{keyword}%';
	@Query("select c from Club c where c.clubName like concat('%', :keyword, '%') or c.clubInfo like concat('%', :keyword, '%')")
	Page<Club> findAllClubsByNameOrInfoLikeKeywords(String keyword, PageRequest createdAt);
}
