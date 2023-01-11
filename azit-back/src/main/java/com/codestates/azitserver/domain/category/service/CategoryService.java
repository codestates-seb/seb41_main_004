package com.codestates.azitserver.domain.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.category.entity.CategoryLarge;
import com.codestates.azitserver.domain.category.entity.CategorySmall;
import com.codestates.azitserver.domain.category.repository.CategoryLargeRepository;
import com.codestates.azitserver.domain.category.repository.CategorySmallRepository;

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
}
