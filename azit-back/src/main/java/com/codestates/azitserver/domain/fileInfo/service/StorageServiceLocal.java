package com.codestates.azitserver.domain.fileInfo.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codestates.azitserver.global.exception.StorageException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile({"local","test", "default"})
public class StorageServiceLocal implements StorageService {
	@Override
	public Map<String, String> upload(String prefix, MultipartFile file) {
		// Path path = Paths.get(System.getProperty("user.dir"), prefix);  // 쉘에서는 사용자 디렉토리로 설정이 되어 경로 문제 발생
		Path path = Paths.get(ClassLoader.getSystemClassLoader().getResource(".").getPath(), prefix);

		// contents exist
		if (file ==null || file.isEmpty()) {
			throw new StorageException("Failed to store empty file.");
		}

		// folder exists
		try {
			if (!Files.isDirectory(path)) {
				Files.createDirectories(path);
			}
		} catch (IOException exception) {
			throw new StorageException("Failed to create save directory.", exception);
		}

		// set unique file name
		String name = UUID.randomUUID().toString();
		String ext = getFileExt(file);
		String filename = name + "." + ext;
		Path dst = path.resolve(filename).normalize().toAbsolutePath();
		if (Files.exists(dst)) {
			throw new StorageException("The File already exists.");
		}
		if (!dst.getParent().equals(path.toAbsolutePath())) {
			throw new StorageException("Cannot store file outside current directory.");
		}

		// copy file
		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, dst, StandardCopyOption.REPLACE_EXISTING);
			log.info("Banner image saved to Local : {}", dst);
		} catch (IOException exception) {
			throw new StorageException("Failed to store file.", exception);
		}

		// fileUrl(파일 저장 경로)와 fileName(파일명)을 Key로 가지는 map을 리턴합니다.
		Map<String, String> map = new HashMap<>();
		map.put("fileUrl", path.toString());
		map.put("fileName", filename);

		return map;
	}
}
