package com.commerce.backendserver.product.intergration;

import com.commerce.backendserver.common.base.IntegrationTestBase;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.persistence.ProductCommandRepository;
import com.commerce.backendserver.product.domain.persistence.promotion.PromotionCommandRepository;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import static com.commerce.backendserver.product.fixture.ProductFixture.VALID_PRODUCT;
import static com.commerce.backendserver.product.fixture.PromotionFixture.VALID_PROMOTION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class ProductFindApiTest extends IntegrationTestBase {

    @Autowired
    private ProductCommandRepository productCommandRepository;

    @Autowired
    private PromotionCommandRepository promotionCommandRepository;

    Product savedProduct;
    Promotion savedPromotion;
    Long id;

    @BeforeEach
    @Rollback(value = false)
    void initProduct() {
        savedPromotion = promotionCommandRepository.save(VALID_PROMOTION.toEntity());
        savedProduct = productCommandRepository.save(VALID_PRODUCT.toEntity(savedPromotion));
        id = savedProduct.getId();
    }

    @Nested
    @DisplayName("[findSingleProduct] 단일 상품 기본 조회")
    class findSingleProduct {

        @Test
        void When_ValidSingleProductPresented_Then_ReturnProductInfo() throws Exception {
            //given - @BeforeEach
            //when && then
            RestAssured
                    .given(spec)
                    .param("id", id)
                    .contentType(APPLICATION_JSON_VALUE)
                    .when()
                    .get("/api/products?id=" + id)
                    .then().log().all()
                    .extract();
        }
    }
}
