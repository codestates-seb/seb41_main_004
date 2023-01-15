package com.codestates.azitserver.domain.club.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codestates.azitserver.domain.category.mapper.CategoryMapper;
import com.codestates.azitserver.domain.club.dto.ClubDto;
import com.codestates.azitserver.domain.club.entity.Club;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ClubMapper {
	@Mapping(target = "clubId", ignore = true)
	@Mapping(target = "clubStatus", ignore = true)
	@Mapping(source = "categorySmallId", target = "categorySmall.categorySmallId")
	Club clubDtoPostToClubEntity(ClubDto.Post post);

	@Mapping(target = "joinMethod", ignore = true)
	@Mapping(target = "joinQuestion", ignore = true)
	@Mapping(target = "clubStatus", ignore = true)
	@Mapping(target = "categorySmall", ignore = true)
	Club clubDtoPatchToClubEntity(ClubDto.Patch patch);

	@Mapping(source = "fileInfo", target = "bannerImage")
	@Mapping(target = "bannerImage.createdAt", expression = "java(null)")
	@Mapping(target = "bannerImage.lastModifiedAt", expression = "java(null)")
	ClubDto.Response clubToClubDtoResponse(Club club);

	List<ClubDto.Response> clubToClubDtoResponse(List<Club> clubList);
}
