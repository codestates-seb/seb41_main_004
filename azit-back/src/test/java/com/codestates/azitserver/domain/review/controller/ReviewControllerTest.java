package com.codestates.azitserver.domain.review.controller;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
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

import com.codestates.azitserver.domain.review.descriptor.ReviewFieldsDescriptor;
import com.codestates.azitserver.domain.review.dto.ReviewDto;
import com.codestates.azitserver.domain.review.entity.Review;
import com.codestates.azitserver.domain.review.helper.ReviewControllerTestHelper;
import com.codestates.azitserver.domain.review.mapper.ReviewMapper;
import com.codestates.azitserver.domain.review.service.ReviewService;
import com.codestates.azitserver.domain.stub.ReviewStubData;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = ReviewController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ReviewControllerTest implements ReviewControllerTestHelper {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	ReviewService reviewService;

	@MockBean
	ReviewMapper mapper;

	Review review;
	ReviewDto.Post post;
	ReviewDto.Patch patch;
	ReviewDto.Get get;
	ReviewDto.Response response;
	Page<Review> reviewPage;

	@BeforeEach
	public void beforeEach() {
		review = ReviewStubData.getDefaultReview();
		post = ReviewStubData.getReviewDtoPost();
		patch = ReviewStubData.getReviewDtoPatch();
		get = ReviewStubData.getReviewDtoGet();
		response = ReviewStubData.getReviewDtoResponse();
		reviewPage = ReviewStubData.getReviewPage();
	}

	@Test
	void postReview() throws Exception {
		// given
		given(mapper.reviewDtoPostToReview(Mockito.any(ReviewDto.Post.class))).willReturn(review);
		given(reviewService.createReview(Mockito.any(), Mockito.any(Review.class))).willReturn(review);
		given(mapper.reviewToReviewDtoResponse(Mockito.any(Review.class))).willReturn(response);

		String content = objectMapper.writeValueAsString(List.of(post));

		// when
		ResultActions actions = mockMvc.perform(postRequestBuilder(getReviewUrl(), content)
			.header("Authorization", "Required JWT access token").characterEncoding(StandardCharsets.UTF_8));

		// then
		actions.andDo(print())
			.andExpect(status().isCreated())
			.andDo(getDefaultDocument("post-review",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					ReviewFieldsDescriptor.getPostRequestFieldsSnippet(),
					ReviewFieldsDescriptor.getSingleArrayResponseFieldsSnippet()
				)
			);
	}

	@Test
	void patchReview() throws Exception {
		// given
		response.setReviewStatus(true);
		given(reviewService.updateReviewStatus(Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean()))
			.willReturn(review);
		given(mapper.reviewToReviewDtoResponse(Mockito.any(Review.class))).willReturn(response);

		String content = objectMapper.writeValueAsString(patch);

		// when
		ResultActions actions = mockMvc.perform(patchRequestBuilder(getReviewUri(), content, 1L)
			.header("Authorization", "Required JWT access token").characterEncoding(StandardCharsets.UTF_8));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument("patch-review",
					pathParameters(List.of(
						parameterWithName("review-id").description("리뷰 고유 식별자"))
					),
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					ReviewFieldsDescriptor.getPatchRequestFieldsSnippet(),
					ReviewFieldsDescriptor.getSingleResponseFieldsSnippet()
				)
			);
	}

	@Test
	void findAllByMember() throws Exception {
		// given
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", "1");
		queryParams.add("size", "10");

		given(reviewService.findReviewByRevieweeId(Mockito.any(), Mockito.anyLong(), Mockito.anyInt(),
			Mockito.anyInt())).willReturn(reviewPage);
		given(mapper.reviewToReviewDtoResponse(Mockito.anyList())).willReturn(List.of(response));

		// when
		ResultActions actions = mockMvc.perform(
			getRequestBuilder(getReviewUrl() + "/reviewee/{reviewee-id}", queryParams, 1L)
				.header("Authorization", "Required JWT access token(Optional)"));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andDo(getDefaultDocument("find-all-by-member",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token(Optional)")),
					pathParameters(List.of(
						parameterWithName("reviewee-id").description("리뷰 대상자 고유 식별자"))
					),
					requestParameters(
						List.of(
							parameterWithName("page").description("Page 번호"),
							parameterWithName("size").description("Page 크기")
						)
					),
					ReviewFieldsDescriptor.getMultiResponseFieldsSnippet()
				)
			);
	}
}