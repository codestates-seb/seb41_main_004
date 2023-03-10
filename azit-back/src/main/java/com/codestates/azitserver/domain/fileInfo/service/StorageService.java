package com.codestates.azitserver.domain.fileInfo.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.codestates.azitserver.global.exception.StorageException;

public interface StorageService {
	/**
	 * 스토리지 서비스를 구현하여 원하는 곳에 파일을 저장합니다.
	 * @param prefix 파일을 저장할 디렉토리
	 * @param file 저장할 파일
	 * @return 파일 정보를 담은 Map을 리턴합니다.
	 */
	Map<String, String> upload(String prefix, MultipartFile file);

	/**
	 * 인자로 주어진 파일의 content type을 통해 확장자를 구합니다.
	 * @param file 파일
	 * @return file의 확장자
	 */
	default String getFileExt(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new StorageException("Failed to store empty file.");
		}

		return file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);
	}
}
