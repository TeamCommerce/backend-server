package com.commerce.backendserver.auth.integration.fixture;

import static lombok.AccessLevel.*;

import java.util.Set;

import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.commerce.backendserver.common.fixture.CommonRequestFixture;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class OAuthRequestFixture {

	private static final String oauthPath = "/api/oauth/{provider}";

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

	private static ValidatableResponse getOAuthRedirectRequest(
		final RequestSpecification spec,
		final Set<RestDocumentationFilter> documentations,
		final String google
	) {
		return CommonRequestFixture.getRequest(RestAssured.given(spec), documentations, oauthPath, google);
	}
}
