package com.commerce.backendserver.review.presentation;

import static com.commerce.backendserver.common.utils.FileMockingUtils.*;
import static com.commerce.backendserver.common.utils.TokenUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.commerce.backendserver.common.base.WebMvcTestBase;
import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.global.config.WebConfig;
import com.commerce.backendserver.review.application.ReviewService;
import com.commerce.backendserver.review.application.dto.request.CreateReviewRequest;

@WebMvcTest(
	value = {ReviewController.class},
	excludeAutoConfiguration = {WebConfig.class})
@DisplayName("[ReviewController Test] - Presentation layer")
public class ReviewControllerTest extends WebMvcTestBase {

	private static final String BASE_URL = "/api/reviews";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReviewService reviewService;

	@Test
	@DisplayName("[registerReview API]")
	void registerReviewTest() throws Exception {
		//given
		Long reviewId = 1L;
		given(reviewService.createReview(any(CreateReviewRequest.class), eq(1L)))
			.willReturn(reviewId);

		MultipartFile file = createMockMultipartFile("hello1.jpg");
		MultiValueMap<String, String> params = generateParams();

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.multipart(BASE_URL)
			.file((MockMultipartFile)file)
			.header(AUTHORIZATION, ACCESS_TOKEN)
			.queryParams(params);

		//when
		ResultActions actions = mockMvc.perform(requestBuilder);

		//then
		actions.andExpect(status().isCreated());
	}

	private MultiValueMap<String, String> generateParams() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ReviewFixture fixture = ReviewFixture.A;

		params.add("contents", fixture.getContents());
		params.add("score", String.valueOf(fixture.getScore()));
		params.add("productId", String.valueOf(1L));
		params.add("productOptionId", String.valueOf(1L));
		fixture.getStringInfoSet().forEach(info -> params.add("additionalInfo", info));

		return params;
	}
}
