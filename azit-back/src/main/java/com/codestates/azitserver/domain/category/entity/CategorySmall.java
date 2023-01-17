package com.codestates.azitserver.domain.category.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.codestates.azitserver.domain.member.entity.MemberCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CategorySmall {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categorySmallId;

	@ManyToOne
	@JoinColumn(name = "CATEGORY_LARGE_ID", updatable = false)
	private CategoryLarge categoryLarge;

	public void setCategoryLarge(CategoryLarge categoryLarge) {
		this.categoryLarge = categoryLarge;
		if (!this.categoryLarge.getCategorySmall().contains(this)) {
			this.categoryLarge.addCategorySmall(this);
		}
	}

	@Column(nullable = false, updatable = false, length = 24)
	private String categoryName;

	@OneToMany(mappedBy = "categorySmall", cascade = CascadeType.ALL)
	private List<MemberCategory> memberCategoryList = new ArrayList<>();

}
