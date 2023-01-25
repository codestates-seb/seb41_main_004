package com.codestates.azitserver.domain.fileInfo.mapper;

import org.jboss.jandex.FieldInfo;
import org.mapstruct.Mapper;

import com.codestates.azitserver.domain.fileInfo.dto.FileInfoDto;

@Mapper(componentModel = "spring")
public interface FileInfoMapper {
	FileInfoDto.Response fileInfoToFileInfoDtoResponse(FieldInfo fieldInfo);
}
