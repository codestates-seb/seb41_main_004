package com.codestates.azitserver.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.azitserver.domain.category.entity.CategorySmall;

public interface CategorySmallRepository extends JpaRepository<CategorySmall, Long> {
}
