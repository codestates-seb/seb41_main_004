package com.codestates.azitserver.domain.follow.controller;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.codestates.azitserver.domain.follow.descrpitor.FollowFieldDescriptor;
import com.codestates.azitserver.domain.follow.dto.FollowDto;
import com.codestates.azitserver.domain.follow.entity.Follow;
import com.codestates.azitserver.domain.follow.helper.FollowControllerTestHelper;
import com.codestates.azitserver.domain.follow.mapper.FollowMapper;
import com.codestates.azitserver.domain.follow.service.FollowService;
import com.codestates.azitserver.domain.stub.FollowStubData;

@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = FollowController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class FollowControllerTest implements FollowControllerTestHelper {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	FollowService followService;

	@MockBean
	FollowMapper mapper;

	Follow follow;
	Page<Follow> followPage;
	FollowDto.FollowStatus followStatus;
	FollowDto.Response response;
	FollowDto.GetFollowerResponse followerResponse;
	FollowDto.GetFollowingResponse followingResponse;

	@BeforeEach
	void beforeEach() {
		// Make stub data
		follow = FollowStubData.getDefaultFollow();
		followPage = FollowStubData.getDefaultPageFollow();
		followStatus = FollowStubData.getFollowDtoFollowStatus();
		response = FollowStubData.getFollowDtoResponse();
		followerResponse = FollowStubData.getFollowDtoGetFollowerResponse();
		followingResponse = FollowStubData.getFollowDtoGetFollowingResponse();

	}

	@Test
	void postFollow() throws Exception {
		// given
		given(followService.verifyMemberAndMemberId(Mockito.any(), Mockito.anyLong())).willReturn(false);
		given(followService.followMember(Mockito.any(), Mockito.anyLong())).willReturn(follow);
		given(mapper.followToFollowDtoResponse(Mockito.any(Follow.class))).willReturn(response);

		// when
		ResultActions actions = mockMvc.perform(postRequestBuilder(getUrl("follow"), "", 1L)
			.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isCreated())
			.andDo(getDefaultDocument(
					"post-follow",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					pathParameters(List.of(
						parameterWithName("member-id").description("회원 고유 식별자"))),
					FollowFieldDescriptor.getFollowDtoResponseFieldsSnippet()
				)
			);
	}

	@Test
	void postUnfollow() throws Exception {
		// given
		given(followService.verifyMemberAndMemberId(Mockito.any(), Mockito.anyLong())).willReturn(false);
		doNothing().when(followService).unfollowMember(Mockito.any(), Mockito.anyLong());

		// when
		ResultActions actions = mockMvc.perform(postRequestBuilder(getUrl("unfollow"), "", 1L)
			.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isNoContent())
			.andDo(getDefaultDocument(
					"post-unfollow",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					pathParameters(List.of(
						parameterWithName("member-id").description("회원 고유 식별자")))
				)
			);
	}

	@Test
	void getFollowers() throws Exception {
		// given
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", "1");
		queryParams.add("size", "10");

		given(followService.findAllMemberFollower(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
			.willReturn(followPage);
		given(mapper.followToFollowDtoGetFollowerResponse(Mockito.anyList())).willReturn(List.of(followerResponse));
		given(followService.verifyMemberAndMemberId(Mockito.any(), Mockito.anyLong())).willReturn(false);

		// when
		ResultActions actions = mockMvc.perform(getRequestBuilder(getUrl("/follower"), queryParams, 1L)
			.header("Authorization", "(Optional) Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument(
					"get-followers",
					requestParameters(
						List.of(
							parameterWithName("page").description("Page 번호"),
							parameterWithName("size").description("Page 크기")
						)
					),
					requestHeaders(headerWithName("Authorization").description("(Optional) Jwt Access Token")),
					pathParameters(List.of(
						parameterWithName("member-id").description("회원 고유 식별자"))),
					FollowFieldDescriptor.getFollowDtoGetFollowerResponseFieldsSnippet()
				)
			);
	}

	@Test
	void getFollowings() throws Exception {
		// given
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", "1");
		queryParams.add("size", "10");

		given(followService.findAllMemberFollowing(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
			.willReturn(followPage);
		given(mapper.followToFollowDtoFollowingResponse(Mockito.anyList())).willReturn(List.of(followingResponse));
		given(followService.verifyMemberAndMemberId(Mockito.any(), Mockito.anyLong())).willReturn(true);

		// when
		ResultActions actions = mockMvc.perform(getRequestBuilder(getUrl("/following"), queryParams, 1L)
			.header("Authorization", "(Optional) Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument(
					"get-followings",
					requestParameters(
						List.of(
							parameterWithName("page").description("Page 번호"),
							parameterWithName("size").description("Page 크기")
						)
					),
					requestHeaders(headerWithName("Authorization").description("(Optional) Jwt Access Token")),
					pathParameters(List.of(
						parameterWithName("member-id").description("회원 고유 식별자"))),
					FollowFieldDescriptor.getFollowDtoGetFollowingResponseFieldsSnippet()
				)
			);
	}

	@Test
	void getFollowStatus() throws Exception {
		// given
		given(mapper.getFollowStatus(Mockito.anyBoolean())).willReturn(followStatus);

		// when
		ResultActions actions = mockMvc.perform(getRequestBuilder(getUrl("follow-status"), 1L)
			.header("Authorization", "(Optional) Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument(
					"get-follow-status",
					requestHeaders(headerWithName("Authorization").description("(Optional) Jwt Access Token")),
					pathParameters(List.of(
						parameterWithName("member-id").description("회원 고유 식별자"))),
					FollowFieldDescriptor.getFollowStatusFieldsSnippet()
				)
			);

	}

	@Test
	void deleteFollowers() throws Exception {
		// given
		given(followService.verifyMemberAndMemberId(Mockito.any(), Mockito.anyLong())).willReturn(true);
		doNothing().when(followService).deleteFollowByFollowId(Mockito.anyLong());

		// when
		ResultActions actions = mockMvc.perform(deleteRequestBuilder(getUrl("follower", "{follow-id}"), 1L, 1L)
			.header("Authorization", "Required JWT access token"));

		// then
		actions.andDo(print())
			.andExpect(status().isNoContent())
			.andDo(getDefaultDocument(
					"delete-followers",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					pathParameters(List.of(
						parameterWithName("member-id").description("회원 고유 식별자"),
						parameterWithName("follow-id").description("팔로우 정보 고유 식별자")))
				)
			);
	}
}