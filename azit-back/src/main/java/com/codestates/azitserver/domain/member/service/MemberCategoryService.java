package com.codestates.azitserver.domain.member.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.member.entity.MemberCategory;
import com.codestates.azitserver.domain.member.repository.MemberCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCategoryService {

	private final MemberCategoryRepository memberCategoryRepository;

	// MemberCategory 조회
	public List<MemberCategory> getAllMemberCategoryByMemberId(Long MemberId) {
		return memberCategoryRepository.findAllMemberCategoryByMemberId(MemberId);
	}

	// 입력한 MemberId를 가진 memberCategory들을 찾아 해당 memberCategory가 가진 CategorySmallId를
	// List에 모아서 반환
	public List<Long> memberCategoryListToCategorySmallIdListByMemberId(Long memberId) {
		List<MemberCategory> foundMemberCategoryList = getAllMemberCategoryByMemberId(
			memberId);

		List<Long> foundCategorySmallIdList = new ArrayList<>();

		for (MemberCategory memberCategory : foundMemberCategoryList) {
			foundCategorySmallIdList.add(memberCategory.getCategorySmall().getCategorySmallId());
		}

		return foundCategorySmallIdList;
	}
}
