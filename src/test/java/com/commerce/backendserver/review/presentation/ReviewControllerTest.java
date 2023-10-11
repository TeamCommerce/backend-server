package com.commerce.backendserver.review.presentation;

import static com.commerce.backendserver.common.utils.FileMockingUtils.*;
import static com.commerce.backendserver.common.utils.TokenUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

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
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.review.application.ReviewAnalyticsService;
import com.commerce.backendserver.review.application.ReviewService;
import com.commerce.backendserver.review.application.dto.request.CreateReviewRequest;
import com.commerce.backendserver.review.application.dto.request.ReviewAnalyticsCondition;
import com.commerce.backendserver.review.fixture.ReviewStatisticFixture;

@WebMvcTest(
	value = {ReviewController.class},
	excludeAutoConfiguration = {WebConfig.class})
@DisplayName("[ReviewController Test] - Presentation layer")
class ReviewControllerTest extends WebMvcTestBase {

	private static final String BASE_URL = "/api/reviews";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReviewService reviewService;

	@MockBean
	private ReviewAnalyticsService reviewAnalyticsService;

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

	@Test
	@DisplayName("[getReviewStatistics API]")
	void getReviewStatisticsTest() throws Exception {
		//given
		ReviewAnalyticsCondition condition = ReviewFixture.getReviewAnalyticsCondition();

		given(reviewAnalyticsService.getReviewStatistics(condition))
			.willReturn(ReviewStatisticFixture.getExpectedReviewStatistics());

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
			.get(BASE_URL + "/statistics")
			.queryParams(
				new LinkedMultiValueMap<>(
					Map.of("engColorNames", condition.engColorNames().stream().toList())))
			.queryParams(
				new LinkedMultiValueMap<>(
					Map.of("sizes", condition.sizes().stream().map(ProductSize::name).toList())))
			.queryParams(
				new LinkedMultiValueMap<>(
					Map.of("scores", condition.scores().stream().map(String::valueOf).toList())))
			.queryParams(
				new LinkedMultiValueMap<>(
					Map.of("additionalOptionValues", condition.additionalOptionValues().stream().toList())))
			.queryParams(
				new LinkedMultiValueMap<>(
					Map.of("additionalInfoValues", condition.additionalInfoValues().stream().toList())));

		//when
		ResultActions actions = mockMvc.perform(requestBuilder);

		//then
		actions.andExpectAll(
			status().isOk(),
			jsonPath("$.totalReviewCount").value(3),
			jsonPath("$.averageScore").value(3.0),
			jsonPath("$.existSizes").exists(),
			jsonPath("$.scoreStatistics").exists(),
			jsonPath("$.additionalInfoStatistics").exists()
		);
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
