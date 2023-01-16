package com.codestates.azitserver.domain.member.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.codestates.azitserver.domain.category.entity.CategorySmall;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MemberCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberCategoryId; // PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member; // FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_SMALL_ID")
	private CategorySmall categorySmall; // FK

}
