package com.commerce.backendserver.review.fixture;

import static com.commerce.backendserver.common.fixture.CommonRequestFixture.*;
import static lombok.AccessLevel.*;

import java.util.Map;
import java.util.Set;

import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.review.application.dto.request.ReviewAnalyticsCondition;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class ReviewStatisticsRequestFixture {

	private static final String BASE_URL = "/api/reviews/statistics";

	public static ValidatableResponse 리뷰_통계_조회를_성공한다(
		final RequestSpecification spec,
		final Set<RestDocumentationFilter> documentations
	) {
		ReviewAnalyticsCondition condition = ReviewFixture.getReviewAnalyticsCondition();

		String path = UriComponentsBuilder
			.fromUriString(BASE_URL)
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
					Map.of("additionalInfoValues", condition.additionalInfoValues().stream().toList())))
			.toUriString();

		return getRequest(
			RestAssured.given(spec),
			documentations,
			path
		);
	}
}
