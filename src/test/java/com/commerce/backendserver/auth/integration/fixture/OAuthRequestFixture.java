package com.commerce.backendserver.auth.integration.fixture;

import static com.commerce.backendserver.common.fixture.CommonRequestFixture.*;
import static lombok.AccessLevel.*;

import java.util.Set;

import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.web.util.UriComponentsBuilder;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class OAuthRequestFixture {

	private static final String OAUTH_PATH = "/api/oauth/{provider}";
	private static final String LOGIN_PATH = "/login/oauth2/code/{provider}";

	public static ValidatableResponse 카카오_소셜로그인_페이지로_이동한다(
		final RequestSpecification spec,
		final Set<RestDocumentationFilter> documentations
	) {
		return getOAuthRedirectRequest(spec, documentations, "kakao");
	}

	public static ValidatableResponse 구글_소셜로그인_페이지로_이동한다(
		final RequestSpecification spec,
		final Set<RestDocumentationFilter> documentations
	) {
		return getOAuthRedirectRequest(spec, documentations, "google");
	}

	public static ValidatableResponse 구글_정보를_통해_로그인한다(
		final RequestSpecification spec,
		final Set<RestDocumentationFilter> documentations
	) {
		return getOAuthLoginRequest(spec, "google", documentations);
	}

	public static ValidatableResponse 카카오_정보를_통해_로그인한다(
		final RequestSpecification spec,
		final Set<RestDocumentationFilter> documentations
	) {
		return getOAuthLoginRequest(spec, "kakao", documentations);
	}

	private static ValidatableResponse getOAuthLoginRequest(
		final RequestSpecification spec,
		final String provider,
		final Set<RestDocumentationFilter> documentations
	) {
		String path = UriComponentsBuilder
			.fromUriString(LOGIN_PATH)
			.queryParam("code", "code")
			.queryParam("state", "state")
			.toUriString().replace("%7B", "{").replace("%7D", "}");

		return getRequest(
			RestAssured.given(spec).log().all(),
			documentations,
			path,
			provider
		);
	}

	private static ValidatableResponse getOAuthRedirectRequest(
		final RequestSpecification spec,
		final Set<RestDocumentationFilter> documentations,
		final String provider
	) {
		return getRequest(
			RestAssured.given(spec),
			documentations,
			OAUTH_PATH,
			provider
		);
	}
}
