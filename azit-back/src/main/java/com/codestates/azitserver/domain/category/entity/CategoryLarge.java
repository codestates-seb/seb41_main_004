package com.codestates.azitserver.domain.category.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CategoryLarge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long CategoryLargeId;

	@Column(nullable = false, updatable = false, length = 24)
	private String CategoryName;

	@OneToMany(mappedBy = "categoryLarge")
	private List<CategorySmall> categorySmall = new ArrayList<>();

	public void addCategorySmall(CategorySmall categorySmall) {
		this.categorySmall.add(categorySmall);
		if (categorySmall.getCategoryLarge() != this) {
			categorySmall.setCategoryLarge(this);
		}
	}
}
