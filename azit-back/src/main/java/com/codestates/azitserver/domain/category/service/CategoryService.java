package com.codestates.azitserver.domain.category.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.category.entity.CategoryLarge;
import com.codestates.azitserver.domain.category.entity.CategorySmall;
import com.codestates.azitserver.domain.category.repository.CategoryLargeRepository;
import com.codestates.azitserver.domain.category.repository.CategorySmallRepository;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryLargeRepository categoryLargeRepository;
	private final CategorySmallRepository categorySmallRepository;

	public List<CategoryLarge> getAllLargeCategory() {
		return categoryLargeRepository.findAll();
	}

	public List<CategorySmall> getAllSmallCategory() {
		return categorySmallRepository.findAll();
	}

	public CategorySmall findCategorySmallById(Long categorySmallId) {
		Optional<CategorySmall> optionalCategorySmall =
			categorySmallRepository.findByCategorySmallId(categorySmallId);
		CategorySmall foundCategoryId =
			optionalCategorySmall.orElseThrow( () ->
				new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
		return foundCategoryId; }
}
