package com.commerce.backendserver.review.integration;

import static com.commerce.backendserver.common.utils.S3LinkUtils.*;
import static com.commerce.backendserver.product.fixture.ProductFixture.VALID_PRODUCT;
import static com.commerce.backendserver.review.exception.ReviewError.*;
import static com.commerce.backendserver.review.integration.ReviewAcceptanceFixture.*;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import com.commerce.backendserver.product.domain.persistence.ProductCommandRepository;
import com.commerce.backendserver.product.fixture.ProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.commerce.backendserver.common.base.IntegrationTestBase;
import com.commerce.backendserver.member.domain.MemberRepository;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductCommonInfo;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;
import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.constants.ProductCategory;

import io.restassured.response.ValidatableResponse;

@DisplayName("[ReviewApi Test] (API)")
class ReviewApiTest extends IntegrationTestBase {

	private static final String REVIEW = "review";

	@MockBean
	private AmazonS3Client amazonS3Client;

	@Autowired
	private ProductCommandRepository productCommandRepository;

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void setUp() throws IOException {
		given(amazonS3Client.putObject(any(PutObjectRequest.class)))
			.willReturn(null);

		final URL mockUrl = new URL(createUploadLink(REVIEW));
		given(amazonS3Client.getUrl(anyString(), anyString()))
			.willReturn(mockUrl);

		productCommandRepository.save(VALID_PRODUCT.toEntity(null));

	}

	@Nested
	@DisplayName("[Register Review API]")
	class 리뷰_등록_API {

		@Test
		@DisplayName("success")
		void success() {
			리뷰를_등록한다(spec, Set.of(documentRequest()), generateAccessToken(memberRepository))
				.statusCode(CREATED.value());

		}

		@Test
		@DisplayName("fail by invalid range score")
		void 잘못된_리뷰_점수_범위로_실패() {
			ValidatableResponse response = 잘못된_리뷰_점수_범위로_실패한다(
				spec,
				Set.of(documentRequest(), documentErrorResponse()),
				generateAccessToken(memberRepository)
			);

			assertErrorResponse(response, BAD_REQUEST, "1에서 5사이의 정수 별점을 입력해주세요.");
		}

		@Test
		@DisplayName("fail by invalid contents length")
		void 잘못된_콘텐츠_길이로_실패() {
			ValidatableResponse response = 잘못된_콘텐츠_길이로_실패한다(
				spec,
				Set.of(documentRequest(), documentErrorResponse()),
				generateAccessToken(memberRepository)
			);

			assertErrorResponse(response, BAD_REQUEST, "최소 5자 이상 입력해주세요.");
		}

		@Test
		@DisplayName("fail by invalid additionalInfo format")
		void 잘못된_추가정보_형식으로_실패() {
			ValidatableResponse response = 잘못된_추가정보_형식으로_실패한다(
				spec,
				Set.of(documentRequest(), documentErrorResponse()),
				generateAccessToken(memberRepository)
			);

			assertErrorResponse(response, BAD_REQUEST, INVALID_ADDITIONAL_INFO.getMessage());
		}

		@Test
		@DisplayName("fail by not exist info name")
		void 존재하지_않는_추가정보_이름으로_실패() {
			ValidatableResponse response = 존재하지_않는_추가정보_이름으로_실패한다(
				spec,
				Set.of(documentRequest(), documentErrorResponse()),
				generateAccessToken(memberRepository)
			);

			assertErrorResponse(response, BAD_REQUEST, NOT_EXIST_INFO_NAME.getMessage());
		}

		@Test
		@DisplayName("fail by invalid integer info value")
		void 숫자형_추가정보에_문자를_입력해_실패() {
			ValidatableResponse response = 숫자형_추가정보에_문자를_입력해_실패한다(
				spec,
				Set.of(documentRequest(), documentErrorResponse()),
				generateAccessToken(memberRepository)
			);

			assertErrorResponse(response, BAD_REQUEST, INVALID_INTEGER_INFO_VALUE.getMessage());
		}
	}

	private RestDocumentationFilter documentRequest() {
		return document(
			DEFAULT_PATH,
			requestParts(
				partWithName("files").description("리뷰 사진"),
				partWithName("contents")
					.description("리뷰 텍스트")
					.attributes(constraint("공백 제외 5자 이상")),
				partWithName("score")
					.description("리뷰 점수")
					.attributes(constraint("1~5 사이 정수")),
				partWithName("productId")
					.description("리뷰할 상품 ID"),
				partWithName("additionalInfo")
					.description("추가정보 이름/추가정보 값")
					.attributes(constraint("지정된 이름만 사용가능"))
			)
		);
	}
}
