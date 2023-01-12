package com.codestates.azitserver.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.azitserver.domain.category.entity.CategoryLarge;

public interface CategoryLargeRepository extends JpaRepository<CategoryLarge, Long> {
}
