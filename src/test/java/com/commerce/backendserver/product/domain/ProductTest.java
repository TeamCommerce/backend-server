package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.image.domain.Image;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import com.commerce.backendserver.product.fixture.ProductFixture;
import com.commerce.backendserver.product.fixture.PromotionFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.commerce.backendserver.product.fixture.ProductFixture.VALID_PRODUCT;
import static com.commerce.backendserver.product.fixture.PromotionFixture.VALID_PROMOTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Product Test] - Domain Layer")
class ProductTest {

    @Nested
    @DisplayName("[createProduct] Method Test")
    class OfTest {

        private final Promotion promotion = VALID_PROMOTION.toEntity();

        @Test
        @DisplayName("[Success] 성공")
        void success() throws Exception {
            //given
            final ProductFixture fixture = VALID_PRODUCT;

            //when
            final Product product = fixture.toEntity(promotion);

            //then
            assertAll(
                    // ProductCommonInfo
                    () -> assertThat(fixture.getBrand()).isEqualTo(product.getCommonInfo().getBrand()),
                    () -> assertThat(fixture.getName()).isEqualTo(product.getCommonInfo().getName()),
                    () -> assertThat(fixture.getCategory()).isEqualTo(product.getCommonInfo().getCategory()),
                    () -> assertThat(fixture.getDescription()).isEqualTo(product.getCommonInfo().getDescription()),
                    // ProductPriceInfo
                    () -> assertThat(fixture.getOriginPrice()).isEqualTo(product.getPriceAttribute().getOriginPrice()),
                    () -> assertThat(promotion).isEqualTo(product.getPriceAttribute().getPromotion()),
                    // ProductImages
                    () -> assertThat(fixture.getImages()).containsAll(product.getImages().stream().map(Image::getUrl).toList())
            );
        }
    }
}
