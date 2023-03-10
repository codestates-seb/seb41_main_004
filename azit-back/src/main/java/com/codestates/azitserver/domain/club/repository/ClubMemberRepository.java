package com.codestates.azitserver.domain.club.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.member.entity.Member;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
	// SELECT * FROM CLUB_MEMBER where CLUB_ID = 1 AND MEMBER_ID = 1
	@Query("select cm from ClubMember cm where cm.member.memberId = :memberId and cm.club.clubId = :clubId")
	Optional<ClubMember> findMemberJoinClub(Long memberId, Long clubId);

	List<ClubMember> findClubMembersByClub(Club Club);

	List<ClubMember> findClubMembersByMember(Member member);
}
