package com.commerce.backendserver.review.integration;

import static com.commerce.backendserver.common.fixture.CommonRequestFixture.*;
import static lombok.AccessLevel.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.springframework.core.io.ClassPathResource;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.web.util.UriComponentsBuilder;

import com.commerce.backendserver.common.fixture.ReviewFixture;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public abstract class ReviewAcceptanceFixture {
	private static final String BASE_URL = "/api/reviews";

	public static ValidatableResponse 리뷰를_등록한다(
		final RequestSpecification spec,
		final Set<RestDocumentationFilter> documentations,
		final String accessToken
	) {
		return getAcceptance(
			spec,
			documentations,
			accessToken,
			params -> {}
		);
	}

	public static ValidatableResponse 잘못된_리뷰_점수_범위로_실패한다(
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

	public static ValidatableResponse 잘못된_콘텐츠_길이로_실패한다(
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

	public static ValidatableResponse 잘못된_추가정보_형식으로_실패한다(
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

	public static ValidatableResponse 존재하지_않는_추가정보_이름으로_실패한다(
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

	public static ValidatableResponse 숫자형_추가정보에_문자를_입력해_실패한다(
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
