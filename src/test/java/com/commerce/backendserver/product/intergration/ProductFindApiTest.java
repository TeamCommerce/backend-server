package com.commerce.backendserver.product.intergration;

import com.commerce.backendserver.common.base.IntegrationTestBase;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.persistence.ProductCommandRepository;
import com.commerce.backendserver.product.domain.persistence.promotion.PromotionCommandRepository;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.annotation.Rollback;

import static com.commerce.backendserver.product.fixture.ProductFixture.VALID_PRODUCT;
import static com.commerce.backendserver.product.fixture.PromotionFixture.VALID_PROMOTION;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@DisplayName("[ProductFindApi Test] (API)")
class ProductFindApiTest extends IntegrationTestBase {

    @Autowired
    private ProductCommandRepository productCommandRepository;

    @Autowired
    private PromotionCommandRepository promotionCommandRepository;

    Product savedProduct;
    Promotion savedPromotion;
    Long id;

    @BeforeEach
    void initProduct() {
        savedPromotion = promotionCommandRepository.save(VALID_PROMOTION.toEntity());
        savedProduct = productCommandRepository.save(VALID_PRODUCT.toEntity(savedPromotion));
        id = savedProduct.getId();
    }

    @Nested
    @DisplayName("[findSingleProduct] 단일 상품 기본 조회")
    class findSingleProduct {

        private static final Snippet PATH_PARAMETERS = pathParameters(
                parameterWithName("id").description("조회할 상품 ID")
        );

        private static final Snippet RESPONSE_FIELDS = responseFields(
                fieldWithPath("id").description("상품 ID"),
                fieldWithPath("originPrice").description("상품 원가"),
                fieldWithPath("promotionType").description("프로모션 타입 [Key]"),
                fieldWithPath("promotionValue").description("프로모션 값 [Value]"),
                fieldWithPath("promotionDiscountedAmount").description("프로모션 할인 금액"),
                fieldWithPath("appliedPromotionPrice").description("프로모션 적용 후 할인가"),
                fieldWithPath("images").description("이미지 URL 목록").type(ARRAY),
                fieldWithPath("options").description("옵션 목록").type(ARRAY),

                // options 배열 안의 객체 필드
                fieldWithPath("options[].optionId").description("옵션 ID"),
                fieldWithPath("options[].color").description("색상 정보").type(OBJECT),
                fieldWithPath("options[].selectionOption").description("선택 옵션 정보").type(OBJECT),
                fieldWithPath("options[].inventory").description("재고"),
                fieldWithPath("options[].status").description("상태"),

                // color 객체 필드
                fieldWithPath("options[].color.colorCode").description("색상 코드"),
                fieldWithPath("options[].color.korColorName").description("한국어 색상 이름"),
                fieldWithPath("options[].color.engColorName").description("영어 색상 이름"),

                // selectionOption 객체 필드
                fieldWithPath("options[].selectionOption.size").description("사이즈"),
                fieldWithPath("options[].selectionOption.key").description("키"),
                fieldWithPath("options[].selectionOption.value").description("값"),
                fieldWithPath("options[].selectionOption.additionalFee").description("추가 비용")
        );

        @Test
        void When_ValidSingleProductPresented_Then_ReturnProductInfo() throws Exception {
            given(spec)
                    .filter(document(DEFAULT_PATH, PATH_PARAMETERS, RESPONSE_FIELDS))
                    .accept(APPLICATION_JSON_VALUE)
                    .header("Content-type", "application/json")
                    .log().all()

                    .when().log().all()
                    .get("/api/products/{id}", savedProduct.getId())

                    .then().log().all()
                    .statusCode(OK.value());
        }
    }
}
