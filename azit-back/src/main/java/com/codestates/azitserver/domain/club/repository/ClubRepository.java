package com.codestates.azitserver.domain.club.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.azitserver.domain.category.entity.CategorySmall;
import com.codestates.azitserver.domain.club.entity.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
	@Query("select c from Club c where c.clubStatus <> 'CLUB_CANCEL'")
	Page<Club> findAllWithoutCanceled(Pageable pageable);

	// SELECT * FROM CLUB INNER JOIN CATEGORY_SMALL WHERE CATEGORY_SMALL.CATEGORY_LARGE_ID = 1 AND CATEGORY_SMALL.CATEGORY_SMALL_ID = CLUB.CATEGORY_SMALL_ID
	@Query("select c from Club c where c.categorySmall.categoryLarge.CategoryLargeId = :categoryLargeId and c.clubStatus <> 'CLUB_CANCEL'")
	Page<Club> findAllClubByCategoryLargeId(Long categoryLargeId, Pageable pageable);

	// SELECT * FROM CLUB WHERE MEETING_DATE = {날짜}
	@Query("select c from Club c where c.meetingDate = :targetDate and c.clubStatus <> 'CLUB_CANCEL'")
	Page<Club> findAllClubsByClubMeetingDate(LocalDate targetDate, Pageable pageable);

	// SELECT * FROM CLUB WHERE CLUB_NAME LIKE '%{keyword}%' OR CLUB_INFO LIKE '%{keyword}%';
	@Query("select c from Club c where c.clubName like concat('%', :keyword, '%') or c.clubInfo like concat('%', :keyword, '%') and c.clubStatus <> 'CLUB_CANCEL'")
	Page<Club> findAllClubsByNameOrInfoLikeKeywords(String keyword, Pageable pageable);

	// SELECT * FROM CATEGORY_SMALL WHERE CATEGORY_SMALL_ID IN (1,7)
	@Query("select c from Club  c where c.categorySmall in :categorySmall and c.clubStatus <> 'CLUB_CANCEL'")
	Page<Club> findAllClubByCategorySmallIds(List<CategorySmall> categorySmall, Pageable pageable);
}
