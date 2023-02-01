package com.codestates.azitserver.domain.category.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.azitserver.domain.category.entity.CategorySmall;

public interface CategorySmallRepository extends JpaRepository<CategorySmall, Long> {
	Optional<CategorySmall> findByCategorySmallId(Long categorySmallId);
}
