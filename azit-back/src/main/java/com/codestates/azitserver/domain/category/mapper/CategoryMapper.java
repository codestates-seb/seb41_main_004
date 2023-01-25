package com.codestates.azitserver.domain.category.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codestates.azitserver.domain.category.dto.CategoryDto;
import com.codestates.azitserver.domain.category.entity.CategoryLarge;
import com.codestates.azitserver.domain.category.entity.CategorySmall;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	List<CategoryDto.Response> categoryLargeToCategoryResponseDto(List<CategoryLarge> categoryLargeList);

	List<CategoryDto.LargeResponse> categoryLargeToCategoryLargeResponseDto(List<CategoryLarge> categoryLargeList);

	@Mapping(source = "categoryLarge.categoryLargeId", target = "categoryLargeId")
	CategoryDto.SmallResponse categorySmallToCategorySmallResponseDto(CategorySmall categorySmall);

	List<CategoryDto.SmallResponse> categorySmallToCategorySmallResponseDto(List<CategorySmall> categorySmallList);
}
