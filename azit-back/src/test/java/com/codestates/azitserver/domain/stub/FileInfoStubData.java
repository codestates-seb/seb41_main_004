package com.codestates.azitserver.domain.stub;

import com.codestates.azitserver.domain.fileInfo.entity.FileInfo;

public class FileInfoStubData {
	public static FileInfo getDefaultFileInfo() {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileId(1L);
		fileInfo.setFileName("이미지파일.png");
		fileInfo.setFileUrl("/folder1/folder2");
		fileInfo.setCreatedAt(null);  // null로 설정하면 Objetc Mapper 설정에 따라 mapping시 표시가 되지 않습니다.
		fileInfo.setLastModifiedAt(null);

		return fileInfo;
	}
}
