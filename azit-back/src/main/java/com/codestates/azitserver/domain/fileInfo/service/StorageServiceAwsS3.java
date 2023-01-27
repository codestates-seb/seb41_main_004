package com.codestates.azitserver.domain.fileInfo.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.codestates.azitserver.global.exception.StorageException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile("server")
@RequiredArgsConstructor
public class StorageServiceAwsS3 implements StorageService {
	@Value("${aws.s3.bucket}")
	private String bucket;

	private final AmazonS3 amazonS3;

	@Override
	public Map<String, String> upload(String prefix, MultipartFile file) {
		// contents exist
		if (file == null || file.isEmpty()) {
			throw new StorageException("Failed to store empty file.");
		}

		// set unique file name
		String name = UUID.randomUUID().toString();
		String ext = getFileExt(file);
		String filename = name + "." + ext;
		try {
			ObjectMetadata objMeta = new ObjectMetadata();
			objMeta.setContentLength(file.getInputStream().available());

			amazonS3.putObject(bucket + prefix, filename, file.getInputStream(), objMeta);
			log.info("Banner image saved to AWS S3 {} : {}", bucket, prefix + "/" + filename);
		} catch (IOException exception) {
			throw new StorageException("Failed to store file.", exception);
		}

		// fileUrl(파일 저장 경로)와 fileName(파일명)을 Key로 가지는 map을 리턴합니다.
		Map<String, String> map = new HashMap<>();
		map.put("fileUrl", prefix);
		map.put("fileName", filename);

		return map;
	}
}


