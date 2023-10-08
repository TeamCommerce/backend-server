package com.commerce.backendserver.review.integration;

import static com.commerce.backendserver.common.utils.S3LinkUtils.*;
import static com.commerce.backendserver.product.fixture.ProductFixture.*;
import static com.commerce.backendserver.product.fixture.PromotionFixture.*;
import static com.commerce.backendserver.review.exception.ReviewError.*;
import static com.commerce.backendserver.review.integration.ReviewAcceptanceFixture.*;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

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
import com.commerce.backendserver.member.infra.persistence.MemberRepository;
import com.commerce.backendserver.product.domain.ProductRepository;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import com.commerce.backendserver.product.infra.persistence.promotion.PromotionCommandRepository;

import io.restassured.response.ValidatableResponse;

@DisplayName("[ReviewApi Test] - Integration")
class ReviewApiTest extends IntegrationTestBase {

	private static final String REVIEW = "review";

	@MockBean
	private AmazonS3Client amazonS3Client;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PromotionCommandRepository promotionCommandRepository;

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void setUp() throws IOException {
		given(amazonS3Client.putObject(any(PutObjectRequest.class)))
			.willReturn(null);

		final URL mockUrl = new URL(createUploadLink(REVIEW));
		given(amazonS3Client.getUrl(anyString(), anyString()))
			.willReturn(mockUrl);

		Promotion savedPromotion = promotionCommandRepository.save(VALID_FIX_PROMOTION.toEntity());
		productRepository.save(VALID_PRODUCT.toEntity(savedPromotion));

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
				partWithName("productOptionId")
					.description("리뷰할 상품의 옵션 ID"),
				partWithName("additionalInfo")
					.description("추가정보 이름/추가정보 값")
					.attributes(constraint("지정된 이름만 사용가능"))
			)
		);
	}

	@Nested
	@DisplayName("[registerReview]")
	class 리뷰_등록 {

		@Test
		@DisplayName("[Success]")
		void success() {
			리뷰_등록_성공(spec, Set.of(documentRequest()), generateAccessToken(memberRepository))
				.statusCode(CREATED.value());

		}

		@Test
		@DisplayName("[Fail] 잘못된 리뷰 점수 범위로 실패")
		void 잘못된_리뷰_점수_범위로_실패() {
			ValidatableResponse response = failByInvalidRangeScope(
				spec,
				Set.of(documentRequest(), documentErrorResponse()),
				generateAccessToken(memberRepository)
			);

			assertErrorResponse(response, BAD_REQUEST, "1에서 5사이의 정수 별점을 입력해주세요.");
		}

		@Test
		@DisplayName("[Fail] 잘못된 콘텐츠 길이로 실패")
		void 잘못된_콘텐츠_길이로_실패() {
			ValidatableResponse response = failWhenPresentInvalidContentLength(
				spec,
				Set.of(documentRequest(), documentErrorResponse()),
				generateAccessToken(memberRepository)
			);

			assertErrorResponse(response, BAD_REQUEST, "최소 5자 이상 입력해주세요.");
		}

		@Test
		@DisplayName("[Fail] 잘못된 추가정보 형식으로 실패")
		void 잘못된_추가정보_형식으로_실패() {
			ValidatableResponse response = failWhenPresentInvalidAdditionalInfoFormat(
				spec,
				Set.of(documentRequest(), documentErrorResponse()),
				generateAccessToken(memberRepository)
			);

			assertErrorResponse(response, BAD_REQUEST, INVALID_ADDITIONAL_INFO.getMessage());
		}

		@Test
		@DisplayName("[Fail] 존재하지 않는 추가정보 이름으로 실패")
		void 존재하지_않는_추가정보_이름으로_실패() {
			ValidatableResponse response = failWhenPresentNonexistenceInfoName(
				spec,
				Set.of(documentRequest(), documentErrorResponse()),
				generateAccessToken(memberRepository)
			);

			assertErrorResponse(response, BAD_REQUEST, NOT_EXIST_INFO_NAME.getMessage());
		}

		@Test
		@DisplayName("[Fail] 숫자형 추가정보에 문자를 입력해 실패")
		void 숫자형_추가정보에_문자를_입력해_실패() {
			ValidatableResponse response = failWhenPresentInvalidIntegerInfoValue(
				spec,
				Set.of(documentRequest(), documentErrorResponse()),
				generateAccessToken(memberRepository)
			);

			assertErrorResponse(response, BAD_REQUEST, INVALID_INTEGER_INFO_VALUE.getMessage());
		}
	}
}
