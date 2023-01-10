package com.codestates.azitserver.domain.club.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codestates.azitserver.domain.club.dto.ClubDto;
import com.codestates.azitserver.domain.club.entity.Club;

@Mapper(componentModel = "spring")
public interface ClubMapper {
	@Mapping(target = "clubId", ignore = true)
	@Mapping(target = "clubStatus", ignore = true)
	Club clubDtoPostToClubEntity(ClubDto.Post post);

	ClubDto.Response clubToClubDtoResponse(Club club);
}