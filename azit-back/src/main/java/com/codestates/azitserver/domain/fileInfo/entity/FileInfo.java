package com.codestates.azitserver.domain.fileInfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.codestates.azitserver.domain.common.Auditable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FileInfo extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fileId;

	@Column(nullable = false, unique = true)
	private String fileName;

	@Column(nullable = false)
	private String fileUrl;
}
