package com.codestates.azitserver.domain.member.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.codestates.azitserver.domain.category.entity.CategorySmall;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "MEMBER_CATEGORY")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberCategoryId; // PK

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	@JsonBackReference
	private Member member; // FK

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_SMALL_ID")
	@JsonBackReference
	private CategorySmall categorySmall; // FK
}
