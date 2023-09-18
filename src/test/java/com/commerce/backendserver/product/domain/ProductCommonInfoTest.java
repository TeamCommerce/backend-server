package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.constants.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.commerce.backendserver.product.domain.constants.ProductBrand.IUP_STUDIO;
import static com.commerce.backendserver.product.domain.constants.ProductCategory.TOP;
import static com.commerce.backendserver.product.exception.ProductError.INVALID_PRODUCT_DESCRIPTION;
import static com.commerce.backendserver.product.exception.ProductError.TOO_LONG_PRODUCT_DESCRIPTION;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[ProductCommonInfo Test] - Domain Layer")
class ProductCommonInfoTest {

    private static final int DESCRIPTION_MAX_LENGTH = 300;

    @Nested
    @DisplayName("[of]")
    class ofTest {

        @Test
        @DisplayName("[Success] 성공")
        void success() {
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
        @DisplayName("[Fail] 상품 주석이 제한 길이 초과일 때 예외를 던진다.")
        void When_NullDescriptionPresented_Then_ThrowException() {
            //given & when & then
            assertThatThrownBy(
                    () ->
                            ProductCommonInfo.of(
                                    IUP_STUDIO,
                                    "Sample",
                                    TOP,
                                    randomAlphanumeric(DESCRIPTION_MAX_LENGTH + 1)))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(TOO_LONG_PRODUCT_DESCRIPTION.getMessage());
        }

        @Test
        @DisplayName("[Fail] 상품 주석이 null 이라면 예외를 던진다.")
        void When_TooLongDescriptionPresented_Then_ThrowException() {
            assertThatThrownBy(
                    () ->
                            ProductCommonInfo.of(
                                    IUP_STUDIO,
                                    "Sample",
                                    TOP,
                                    null))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(INVALID_PRODUCT_DESCRIPTION.getMessage());
        }
    }
}
