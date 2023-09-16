package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.commerce.backendserver.product.exception.ProductError.INVALID_PRICE_ATTRIBUTE;
import static com.commerce.backendserver.product.fixture.PromotionFixture.VALID_PROMOTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[ProductPriceAttribute Test] - Domain Layer")
class ProductPriceAttributeTest {


    @Nested
    @DisplayName("[ProductPriceAttribute.of] Method Test")
    class ofTest {

        @Test
        @DisplayName("[Success] 성공")
        void success() throws Exception {
            //given
            final Promotion promotion = VALID_PROMOTION.toEntity();
            final Integer originPrice = 30000;

            //when
            ProductPriceAttribute result = ProductPriceAttribute.of(
                    promotion,
                    originPrice
            );

            //then
            assertAll(
                    () -> assertThat(result.getPromotion()).isEqualTo(promotion),
                    () -> assertThat(result.getOriginPrice()).isEqualTo(originPrice)
            );
        }

        @Test
        @DisplayName("[Fail] 상품 가격(originPrice)이 음의 정수일 때 예외를 던진다.")
        void When_IsOriginPriceMinus_Then_ThrowException() throws Exception {
            //given & when & then
            assertThatThrownBy(
                    () ->
                            ProductPriceAttribute.of(VALID_PROMOTION.toEntity(), -3000))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(INVALID_PRICE_ATTRIBUTE.getMessage());
        }
    }

    @Nested
    @DisplayName("[applyPromotionDiscount] Method Test")
    class applyPromotionDiscountTest {

        @Test
        @DisplayName("")
        void test() throws Exception {
            //given


            //when


            //then
        }
    }
}
