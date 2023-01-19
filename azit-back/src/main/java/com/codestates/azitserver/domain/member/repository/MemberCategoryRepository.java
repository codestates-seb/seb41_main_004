package com.codestates.azitserver.domain.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.entity.MemberCategory;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {
	@Query("select m from MEMBER_CATEGORY m where m.member.memberId = :memberId")
	List<MemberCategory> findAllMemberCategoryByMemberId(Long memberId);

	void deleteAllByMember(Member member);
}
