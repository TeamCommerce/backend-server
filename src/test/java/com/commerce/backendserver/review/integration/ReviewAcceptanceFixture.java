package com.commerce.backendserver.review.integration;

import com.commerce.backendserver.common.fixture.ReviewFixture;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static com.commerce.backendserver.common.fixture.CommonRequestFixture.multipartRequest;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public abstract class ReviewAcceptanceFixture {
	private static final String BASE_URL = "/api/reviews";

	public static ValidatableResponse 리뷰_등록_성공(
			final RequestSpecification spec,
			final Set<RestDocumentationFilter> documentations,
			final String accessToken
	) {
		return getAcceptance(
				spec,
				documentations,
				accessToken,
				params -> {
				}
		);
	}

	public static ValidatableResponse failByInvalidRangeScope(
			final RequestSpecification spec,
			final Set<RestDocumentationFilter> documentations,
			final String accessToken
	) {
		return getAcceptance(
				spec,
				documentations,
				accessToken,
				params -> params.put("score", String.valueOf(10))
		);
	}

	public static ValidatableResponse failWhenPresentInvalidContentLength(
			final RequestSpecification spec,
			final Set<RestDocumentationFilter> documentations,
			final String accessToken
	) {
		return getAcceptance(
				spec,
				documentations,
				accessToken,
				params -> params.put("contents", "a a a")
		);
	}

	public static ValidatableResponse failWhenPresentInvalidAdditionalInfoFormat(
			final RequestSpecification spec,
			final Set<RestDocumentationFilter> documentations,
			final String accessToken
	) {
		return getAcceptance(
				spec,
				documentations,
				accessToken,
				params -> params.put("additionalInfo", "SIZE|Large")
		);
	}

	public static ValidatableResponse failWhenPresentNonexistenceInfoName(
			final RequestSpecification spec,
			final Set<RestDocumentationFilter> documentations,
			final String accessToken
	) {
		return getAcceptance(
				spec,
				documentations,
				accessToken,
				params -> params.put("additionalInfo", "HELLO/Large")
		);
	}

	public static ValidatableResponse failWhenPresentInvalidIntegerInfoValue(
			final RequestSpecification spec,
			final Set<RestDocumentationFilter> documentations,
			final String accessToken
	) {
		return getAcceptance(
				spec,
				documentations,
				accessToken,
				params -> params.put("additionalInfo", "HEIGHT/172cm")
		);
	}

	private static ValidatableResponse getAcceptance(
		RequestSpecification spec,
		Set<RestDocumentationFilter> documentations,
		String accessToken,
		Consumer<Map<String, String>> paramManger
	) {
		final String path = generatePath();

		final List<File> files = List.of(getFile());

		final Map<String, String> params = fetchSuccessRequestParams();
		paramManger.accept(params);

		return multipartRequest(
			RestAssured.given(spec),
			documentations,
			accessToken,
			files,
			params,
			path
		);
	}

	private static String generatePath() {
		return UriComponentsBuilder
			.fromPath(BASE_URL)
			.build()
			.toUri()
			.getPath();
	}

	private static File getFile() {
		final String BASE_PATH = "images/";
		try {
			return new ClassPathResource(BASE_PATH + "hello1.jpg").getFile();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Map<String, String> fetchSuccessRequestParams() {
		Map<String, String> params = new HashMap<>();
		ReviewFixture fixture = ReviewFixture.A;

		params.put("contents", fixture.getContents());
		params.put("score", String.valueOf(fixture.getScore()));
		params.put("productId", String.valueOf(1L));
		fixture.getStringInfoSet().forEach(info -> params.put("additionalInfo", info));

		return params;
	}
}
