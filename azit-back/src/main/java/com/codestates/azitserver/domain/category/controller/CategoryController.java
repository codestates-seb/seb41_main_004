package com.codestates.azitserver.domain.category.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.azitserver.domain.category.dto.CategoryDto;
import com.codestates.azitserver.domain.category.entity.CategoryLarge;
import com.codestates.azitserver.domain.category.entity.CategorySmall;
import com.codestates.azitserver.domain.category.mapper.CategoryMapper;
import com.codestates.azitserver.domain.category.service.CategoryService;
import com.codestates.azitserver.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
	private final CategoryService categoryService;
	private final CategoryMapper mapper;


	@GetMapping
	public ResponseEntity<?> getAllCategory() {
		List<CategoryLarge> categoryLargeList = categoryService.getAllLargeCategory();
		List<CategoryDto.Response> responses = mapper.categoryLargeToCategoryResponseDto(categoryLargeList);

		return new ResponseEntity<>(new SingleResponseDto<>(responses), HttpStatus.OK);
	}

	@GetMapping("/large")
	public ResponseEntity<?> getLargeCategory() {
		List<CategoryLarge> categoryLargeList = categoryService.getAllLargeCategory();
		List<CategoryDto.LargeResponse> responses = mapper.categoryLargeToCategoryLargeResponseDto(categoryLargeList);

		return new ResponseEntity<>(new SingleResponseDto<>(responses), HttpStatus.OK);
	}

	@GetMapping("/small")
	public ResponseEntity<?> getSmallCategory() {
		List<CategorySmall> categorySmallList = categoryService.getAllSmallCategory();
		List<CategoryDto.SmallResponse> responses = mapper.categorySmallToCategorySmallResponseDto(categorySmallList);

		return new ResponseEntity<>(new SingleResponseDto<>(responses), HttpStatus.OK);
	}
}
