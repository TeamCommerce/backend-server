package com.commerce.backendserver.auth.integration;

import static com.commerce.backendserver.auth.integration.fixture.OAuthRequestFixture.*;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.commerce.backendserver.common.base.IntegrationTestBase;

import io.restassured.response.ValidatableResponse;

@DisplayName("[OAuthApi Test] - Integration")
class OAuthApiTest extends IntegrationTestBase {

	@Nested
	@DisplayName("[redirectToOAuthAuthorization]")
	class 소셜로그인_페이지로_리다이렉트 {

		@Test
		@DisplayName("[Success] 구글 로그인 페이지로 리다이랙트한다")
		void 구글() {
			ValidatableResponse response = 구글_소셜로그인_페이지로_이동한다(
				spec, Set.of(redirectToOAuthAuthorizationDocuments())
			);

			response.statusCode(HttpStatus.FOUND.value());
		}

		@Test
		@DisplayName("[Success] 카카오 로그인 페이지로 리다이렉트한다")
		void 카카오() {
			ValidatableResponse response = 카카오_소셜로그인_페이지로_이동한다(
				spec, Set.of(redirectToOAuthAuthorizationDocuments())
			);

			response.statusCode(HttpStatus.FOUND.value());
		}
	}

	private RestDocumentationFilter redirectToOAuthAuthorizationDocuments() {
		return document(
			DEFAULT_PATH,
			pathParameters(
				parameterWithName("provider").description("소셜로그인 제공")
					.attributes(constraint("구글, 카카오만 지원함"))
			)
		);
	}

}
