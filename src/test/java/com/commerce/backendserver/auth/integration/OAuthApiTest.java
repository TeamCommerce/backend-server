package com.commerce.backendserver.auth.integration;

import static com.commerce.backendserver.auth.integration.fixture.OAuthRequestFixture.*;
import static com.commerce.backendserver.common.utils.TokenUtils.*;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.web.client.RestTemplate;

import com.commerce.backendserver.auth.domain.model.OAuthMemberInfo;
import com.commerce.backendserver.auth.domain.model.OAuthTokenInfo;
import com.commerce.backendserver.auth.infra.oauth.google.model.GoogleMemberInfo;
import com.commerce.backendserver.auth.infra.oauth.google.model.GoogleTokenInfo;
import com.commerce.backendserver.common.base.IntegrationTestBase;

import io.restassured.response.ValidatableResponse;

@DisplayName("[OAuthApi Test] - Integration")
class OAuthApiTest extends IntegrationTestBase {

	@MockBean
	private RestTemplate restTemplate;

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

	@Nested
	@DisplayName("[login]")
	class 소셜로그인 {

		@BeforeEach
		void setUp() {
			OAuthTokenInfo oAuthTokenInfo = new GoogleTokenInfo(null, ACCESS_TOKEN, null, 10L);
			OAuthMemberInfo oauthMemberInfo = new GoogleMemberInfo("id", "name", "email");

			given(restTemplate.exchange(
				anyString(),
				any(HttpMethod.class),
				any(HttpEntity.class),
				any(Class.class)
			)).willReturn(ResponseEntity.ok(oauthMemberInfo));

			given(restTemplate.postForObject(
				anyString(),
				any(HttpEntity.class),
				any())
			).willReturn(oAuthTokenInfo);
		}

		@Test
		@DisplayName("[Success] 구글 정보를 통해서 로그인한다")
		void 구글() {
			ValidatableResponse response = 구글_정보를_통해_로그인한다(spec);

			response
				.statusCode(200)
				.body("accessToken", notNullValue())
				.body("refreshToken", notNullValue());
		}

		@Test
		@DisplayName("[Success] 카카오 정보를 통해서 로그인한다")
		void 카카오() {
			ValidatableResponse response = 카카오_정보를_통해_로그인한다(spec);

			response
				.statusCode(200)
				.body("accessToken", notNullValue())
				.body("refreshToken", notNullValue());
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
