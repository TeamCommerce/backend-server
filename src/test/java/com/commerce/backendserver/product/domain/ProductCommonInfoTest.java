package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.global.exception.error.ErrorCode;
import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.constants.ProductCategory;
import com.commerce.backendserver.product.exception.ProductError;
import com.commerce.backendserver.product.fixture.ProductFixture;
import com.commerce.backendserver.product.fixture.PromotionFixture;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.commerce.backendserver.product.domain.constants.ProductBrand.IUP_STUDIO;
import static com.commerce.backendserver.product.domain.constants.ProductCategory.TOP;
import static com.commerce.backendserver.product.exception.ProductError.INVALID_PRODUCT_DESCRIPTION;
import static com.commerce.backendserver.product.exception.ProductError.TOO_LONG_PRODUCT_DESCRIPTION;
import static com.commerce.backendserver.product.fixture.ProductFixture.NULL_DESCRIPTION;
import static com.commerce.backendserver.product.fixture.ProductFixture.TOO_LONG_DESCRIPTION;
import static com.commerce.backendserver.product.fixture.PromotionFixture.VALID_PROMOTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Product Test] - Domain Layer")
class ProductCommonInfoTest {



    @Nested
    @DisplayName("[createProduct] Method Test")
    class ofTest {

        @Test
        @DisplayName("[Success] 성공")
        void success() throws Exception {
            //given
            final ProductBrand brand = IUP_STUDIO;
            final String name = "아이없 상의";
            final ProductCategory category = TOP;
            final String description = "따뜻해요";

            //when
            ProductCommonInfo result = ProductCommonInfo.of(
                    brand,
                    name,
                    category,
                    description
            );

            //then
            assertAll(
                    () -> assertThat(result.getBrand()).isEqualTo(brand),
                    () -> assertThat(result.getName()).isEqualTo(name),
                    () -> assertThat(result.getCategory()).isEqualTo(category),
                    () -> assertThat(result.getDescription()).isEqualTo(description)
            );
        }

        @Test
        @DisplayName("[Fail] 상품 주석이 제한 길이 초과일 때 적절한 예외를 던진다.")
        void When_NullDescriptionPresented_Then_ThrowException() throws Exception {
            //given & when & then
            assertThatThrownBy(() -> NULL_DESCRIPTION.toEntity(VALID_PROMOTION.toEntity()))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(INVALID_PRODUCT_DESCRIPTION.getMessage());
        }

        @Test
        @DisplayName("[Fail] 상품 주석이 null 이라면 예외를 던진다.")
        void When_TooLongDescriptionPresented_Then_ThrowException() throws Exception {
            //given & when & then
            assertThatThrownBy(() -> TOO_LONG_DESCRIPTION.toEntity(VALID_PROMOTION.toEntity()))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(TOO_LONG_PRODUCT_DESCRIPTION.getMessage());
        }
    }
}
