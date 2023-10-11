package com.commerce.backendserver.review.integration;

import static com.commerce.backendserver.common.fixture.ReviewFixture.*;
import static com.commerce.backendserver.product.fixture.ProductFixture.*;
import static com.commerce.backendserver.product.fixture.PromotionFixture.*;
import static com.commerce.backendserver.review.fixture.ReviewStatisticsRequestFixture.*;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.commerce.backendserver.common.base.IntegrationTestBase;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductRepository;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import com.commerce.backendserver.product.infra.persistence.promotion.PromotionCommandRepository;
import com.commerce.backendserver.review.domain.ReviewRepository;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder;

import io.restassured.response.ValidatableResponse;

@DisplayName("[ReviewApi Test] - Integration")
class ReviewStatisticsApiTest extends IntegrationTestBase {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PromotionCommandRepository promotionCommandRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@BeforeEach
	void setUp() {
		Promotion savedPromotion = promotionCommandRepository.save(VALID_FIX_PROMOTION.toEntity());
		Product product = productRepository.save(VALID_PRODUCT.toEntity(savedPromotion));
		Long optionId = product.getOptions().get(0).getId();

		reviewRepository.save(A.toEntity(product, 1L, optionId));
		reviewRepository.save(A.toEntity(product, 1L, optionId));
		reviewRepository.save(A.toEntity(product, 1L, optionId));
	}

	@Nested
	@DisplayName("[getReviewStatistics]")
	class 리뷰_통계_조회 {

		@Test
		@DisplayName("[Success]")
		void 리뷰_통계_조회_성공() {
			ValidatableResponse response = 리뷰_통계_조회를_성공한다(spec, Set.of(requestDocument()));

			response.statusCode(200);
		}
	}

	private RestDocumentationFilter requestDocument() {
		return document(
			DEFAULT_PATH,
			customReviewStatisticsApiSwagger(),
			queryParameters(
				parameterWithName("engColorNames").description("상품 색상").optional(),
				parameterWithName("sizes").description("상품 사이즈").optional(),
				parameterWithName("additionalOptionValues").description("상품 개별 옵션").optional(),
				parameterWithName("scores").description("리뷰 점수").optional(),
				parameterWithName("additionalInfoValues").description("리뷰 추가정보(설문정보)").optional()
			),
			responseFields(
				fieldWithPath("totalReviewCount").description("총 리뷰어 수"),
				fieldWithPath("averageScore").description("평균 리뷰 점수"),
				fieldWithPath("existSizes").description("리뷰된 상품의 사이즈"),
				fieldWithPath("scoreStatistics").description("리뷰 점수 통계"),
				fieldWithPath("scoreStatistics.3")
					.description("평점 3의 통계 정보"),
				fieldWithPath("scoreStatistics.3.reviewers")
					.description("평점 3을 준 리뷰어 수"),
				fieldWithPath("scoreStatistics.3.ratio")
					.description("평점 3의 비율 (백분율)"),
				fieldWithPath("additionalInfoStatistics")
					.description("추가 정보 통계 정보"),
				fieldWithPath("additionalInfoStatistics.키")
					.description("키 추가 정보에 대한 통계 정보"),
				fieldWithPath("additionalInfoStatistics.키.172")
					.description("키 172에 대한 통계 정보"),
				fieldWithPath("additionalInfoStatistics.키.172.reviewers")
					.description("키 172를 선택한 리뷰어 수"),
				fieldWithPath("additionalInfoStatistics.키.172.ratio")
					.description("키 172의 비율 (백분율)"),
				fieldWithPath("additionalInfoStatistics.사이즈")
					.description("사이즈 추가 정보에 대한 통계 정보"),
				fieldWithPath("additionalInfoStatistics.사이즈.생각보다_큼")
					.description("사이즈 '생각보다 큼' 에 대한 통계 정보"),
				fieldWithPath("additionalInfoStatistics.사이즈.생각보다_큼.reviewers")
					.description("사이즈 '생각보다 큼'를 선택한 리뷰어 수"),
				fieldWithPath("additionalInfoStatistics.사이즈.생각보다_큼.ratio")
					.description("사이즈 '생각보다 큼' 비율 (백분율)")
			)
		);
	}

	private ResourceSnippetParametersBuilder customReviewStatisticsApiSwagger() {
		return ResourceSnippetParameters.builder()
			.tag("리뷰 API")
			.summary("리뷰 통계 조회 API - @eunchannam")
			.links()
			.description("선택한 상품의 리뷰 통계를 조회하는 API");
	}
}
