package com.codestates.azitserver.global.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.codestates.azitserver.domain.category.entity.CategoryLarge;

@DataJpaTest
@EnableQueryLog
@ActiveProfiles("local")
public class P6SpySqlFormatterTest {
	@Autowired
	TestEntityManager em;

	@Test
	@DisplayName("P6Spy 추가로 쿼리문이 올바르게 콘솔에 출력됩니다.")
	void test() {
		CategoryLarge categoryLarge = em.find(CategoryLarge.class, 1L);
	}
}
