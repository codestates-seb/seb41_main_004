package com.codestates.azitserver.domain.fileInfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FileInfoDto {
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Long fileId;
		private String fileUrl;
		private String fileName;
	}
}
