package com.codestates.azitserver.domain.category.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.azitserver.domain.category.entity.CategorySmall;
import com.codestates.azitserver.domain.member.entity.Member;

public interface CategorySmallRepository extends JpaRepository<CategorySmall, Long> {
	Optional<CategorySmall> findByCategorySmallId(Long categorySmallId);
}
