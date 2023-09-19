package com.commerce.backendserver.product.integration;

import com.commerce.backendserver.common.base.IntegrationTestBase;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import com.commerce.backendserver.product.infra.persistence.ProductCommandRepository;
import com.commerce.backendserver.product.infra.persistence.promotion.PromotionCommandRepository;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.snippet.Snippet;

import static com.commerce.backendserver.product.fixture.ProductFixture.VALID_PRODUCT;
import static com.commerce.backendserver.product.fixture.PromotionFixture.*;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.Schema.schema;
import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@DisplayName("[ProductFindApi Test] - Integration")
class ProductFindApiTest extends IntegrationTestBase {

    @Autowired
    private ProductCommandRepository productCommandRepository;

    @Autowired
    private PromotionCommandRepository promotionCommandRepository;

    Long id;
    Product savedProduct;
    Promotion savedPromotion;

    @BeforeEach
    void setUp() {
        savedPromotion = promotionCommandRepository.save(VALID_FIX_PROMOTION.toEntity());
        savedProduct = productCommandRepository.save(VALID_PRODUCT.toEntity(savedPromotion));
        id = savedProduct.getId();
    }

    @Nested
    @DisplayName("[findSingleProduct]")
    class 단일_상품_상세조회 {

        private static ResourceSnippetParametersBuilder swaggerDescriptionWithResponseSchema(
                String ResponseSchema
        ) {
            return ResourceSnippetParameters.builder()
                    .tag("SingleProduct - 단일 상품 관련 API")
                    .summary("단일 상품 상세 조회 API - @h.beeen")
                    .links()
                    .description("상품 ID를 활용해 기본 정보(메인페이지 필요 정보)를 조회하는 API")
                    .requestSchema(schema("[FindSingleProduct] Request - GET"))
                    .responseSchema(schema(ResponseSchema));
        }

        private static final Snippet PATH_PARAMETERS = pathParameters(
                parameterWithName("id").description("조회할 상품 ID")
        );

        private static final Snippet RESPONSE_FIELDS = responseFields(
                fieldWithPath("product.id").description("상품 ID"),
                fieldWithPath("product.name").description("상품 이름"),
                fieldWithPath("product.brand").description("상품 브랜드"),
                fieldWithPath("product.description").description("상품 설명"),
                fieldWithPath("product.originPrice").description("상품 정상가"),
                fieldWithPath("product.promotionType").description("프로모션 타입"),
                fieldWithPath("product.promotionValue").description("프로모션 값"),
                fieldWithPath("product.finalPrice").description("상품 최종 판매가"),
                fieldWithPath("product.colors").description("상품 색상코드 리스트").type(ARRAY),

                fieldWithPath("images").description("이미지 URL 목록").type(ARRAY),

                fieldWithPath("options[].optionId").description("옵션 ID"),
                fieldWithPath("options[].color").description("색상 정보").type(OBJECT),
                fieldWithPath("options[].color.colorCode").description("색상 코드"),
                fieldWithPath("options[].color.korColorName").description("한글 색상 이름"),
                fieldWithPath("options[].color.engColorName").description("영어 색상 이름"),
                fieldWithPath("options[].inventory").description("재고"),
                fieldWithPath("options[].status").description("상태"),
                fieldWithPath("options[].size").description("사이즈"),
                fieldWithPath("options[].additionalOption.key").description("추가 옵션 키"),
                fieldWithPath("options[].additionalOption.value").description("추가 옵션 값"),
                fieldWithPath("options[].additionalOption.additionalFee").description("추가 옵션 추가 비용")
        );

        private static final Snippet ERROR_RESPONSE = responseFields(
                fieldWithPath("timeStamp").description("에러 발생 시간"),
                fieldWithPath("errorCode").description("발생 에러 httpStatus"),
                fieldWithPath("errorMessage").description("에러 메세지")
        );

        @Test
        @DisplayName("[Success]")
        void 성공() {
            given(spec)
                    .filter(document(DEFAULT_PATH,
                            swaggerDescriptionWithResponseSchema("[FindSingleResponse] FindSingleProductResponse (GET)"),
                            PATH_PARAMETERS, RESPONSE_FIELDS))
                    .accept(APPLICATION_JSON_VALUE)
                    .header("Content-type", "application/json")
                    .log().all()

                    .when().log().all()
                    .get("/api/products/{id}", savedProduct.getId())

                    .then().log().all()
                    .statusCode(OK.value())
                    .extract();
        }

        @Test
        @DisplayName("[Fail] 존재하지 않는 상품 조회로 실패")
        void 존재하지_않는_상품_조회시_실패() {
            given(spec)
                    .filter(document(DEFAULT_PATH,
                            swaggerDescriptionWithResponseSchema("[FindSingleResponse] Member Not Found (404)"),
                            PATH_PARAMETERS, ERROR_RESPONSE))
                    .accept(APPLICATION_JSON_VALUE)
                    .header("Content-type", "application/json")
                    .log().all()

                    .when().log().all()
                    .get("/api/products/{id}", -2147483648)

                    .then().log().all()
                    .statusCode(NOT_FOUND.value())
                    .extract();
        }

        @Test
        @DisplayName("[Fail] 비정상 정액 프로모션으로 실패(originPrice - discountedAmount < 0)")
        void 정가보다_정액_할인_프로모션_가격이_크므로_실패() {
            //given
            Promotion tooHighPromotion = promotionCommandRepository.save(TOO_HIGH_PROMOTION.toEntity());
            Product minusPriceProduct = productCommandRepository.save(VALID_PRODUCT.toEntity(tooHighPromotion));

            //when && then
            given(spec)
                    .filter(document(DEFAULT_PATH,
                            swaggerDescriptionWithResponseSchema("[FindSingleResponse] Bad Request (400)"),
                            PATH_PARAMETERS, ERROR_RESPONSE))
                    .accept(APPLICATION_JSON_VALUE)
                    .header("Content-type", "application/json")
                    .log().all()

                    .when().log().all()
                    .get("/api/products/{id}", minusPriceProduct.getId())

                    .then().log().all()
                    .statusCode(BAD_REQUEST.value())
                    .extract();
        }

        @Test
        @DisplayName("[Fail] 비정상 정액 프로모션(discountedAmount 음수")
        void 정액_할인_프로모션_값이_음수면_실패() {
            //given
            Promotion minusPromotion = promotionCommandRepository.save(MINUS_FIX_PROMOTION.toEntity());
            Product product = productCommandRepository.save(VALID_PRODUCT.toEntity(minusPromotion));

            //when && then
            given(spec)
                    .filter(document(DEFAULT_PATH,
                            swaggerDescriptionWithResponseSchema("[FindSingleResponse] Bad Request (400)"),
                            PATH_PARAMETERS, ERROR_RESPONSE))
                    .accept(APPLICATION_JSON_VALUE)
                    .header("Content-type", "application/json")
                    .log().all()


                    .when().log().all()
                    .get("/api/products/{id}", product.getId())

                    .then().log().all()
                    .statusCode(BAD_REQUEST.value())
                    .extract();
        }
    }
}
