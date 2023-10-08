package com.commerce.backendserver.product.infra.persistence;

import com.commerce.backendserver.common.base.RepositoryTestBase;
import com.commerce.backendserver.image.domain.ProductImage;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductCommonInfo;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;
import com.commerce.backendserver.product.domain.ProductRepository;
import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import com.commerce.backendserver.product.infra.persistence.promotion.PromotionCommandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.commerce.backendserver.product.fixture.ProductFixture.VALID_PRODUCT;
import static com.commerce.backendserver.product.fixture.PromotionFixture.VALID_FIX_PROMOTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[ProductQueryRepository Test] - Infra Layer")
class ProductQueryRepositoryTest extends RepositoryTestBase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PromotionCommandRepository promotionCommandRepository;

    private void assertImagesMatching(List<ProductImage> actual, List<ProductImage> expected) {
        assertThat(actual).hasSameSizeAs(expected);
        IntStream.range(0, actual.size())
                .forEach(i -> {
                    ProductImage actualImage = actual.get(i);
                    ProductImage expectedImage = expected.get(i);
                    assertAll(
                            () -> assertThat(actualImage.getImageCategory()).isEqualTo(expectedImage.getImageCategory()),
                            () -> assertThat(actualImage.getId()).isEqualTo(expectedImage.getId()),
                            () -> assertThat(actualImage.getUrl()).isEqualTo(expectedImage.getUrl())
                    );
                });
    }

    private void assertOptionsMatching(List<ProductOption> actual, List<ProductOption> expected) {
        assertThat(actual).hasSameSizeAs(expected);
        IntStream.range(0, actual.size())
                .forEach(i -> {
                    ProductOption actualOption = actual.get(i);
                    ProductOption expectedOption = expected.get(i);
                    assertAll(
                            () -> assertThat(actualOption.getId()).isEqualTo(expectedOption.getId()),
                            () -> assertThat(actualOption.getInventory()).isEqualTo(expectedOption.getInventory()),
                            () -> assertThat(actualOption.getAdditionalOption().getAdditionalFee())
                                    .isEqualTo(expectedOption.getAdditionalOption().getAdditionalFee()),
                            () -> assertThat(actualOption.getColor()).isEqualTo(expectedOption.getColor()),
                            () -> assertThat(actualOption.getSize()).isEqualTo(expectedOption.getSize())
                    );
                });
    }

    private void assertProductCommonInfoMatching(
            final ProductCommonInfo actual,
            final ProductCommonInfo expected
    ) {
        assertAll(
                () -> assertThat(actual.getDescription()).isEqualTo(expected.getDescription()),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getCategory()).isEqualTo(expected.getCategory()),
                () -> assertThat(actual.getBrand()).isEqualTo(expected.getBrand())
        );
    }

    private void assertProductPriceAttributeMatching(
            final ProductPriceAttribute actual,
            final ProductPriceAttribute expected
    ) {
        assertAll(
                () -> assertThat(actual.getOriginPrice()).isEqualTo(expected.getOriginPrice()),
                () -> assertPromotionMatching(actual.getPromotion(), expected.getPromotion()),
                () -> assertThat(actual.getPromotionDiscountedValue())
                        .isEqualTo(actual.getPromotionDiscountedValue())
        );
    }

    private void assertPromotionMatching(final Promotion actual, final Promotion expected) {
        assertAll(
                () -> assertThat(actual.getPromotionValue())
                        .isEqualTo(expected.getPromotionValue()),
                () -> assertThat(actual.getPromotionValue())
                        .isEqualTo(expected.getPromotionValue()),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Nested
    @DisplayName("[findWithOptionsById]")
    class findWithOptionsByIdTest {

        @Test
        @DisplayName("[Success]")
        void success() {
            //given
            Promotion promotion = promotionCommandRepository.save(VALID_FIX_PROMOTION.toEntity());
            Product expected = productRepository.save(VALID_PRODUCT.toEntity(promotion));

            //when
            Optional<Product> result = productRepository.findDistinctWithOptionsById(expected.getId());

            //then
            assertThat(result).isPresent();

            Product actual = result.get();
            assertAll(
                    () -> assertImagesMatching(actual.getImages(), expected.getImages()),
                    () -> assertOptionsMatching(actual.getOptions(), expected.getOptions()),
                    () -> assertProductCommonInfoMatching(actual.getCommonInfo(), expected.getCommonInfo()),
                    () -> assertProductPriceAttributeMatching(actual.getPriceAttribute(), expected.getPriceAttribute())
            );
        }

        @Test
        @DisplayName("[Fail] 해당 id 에 맞는 상품이 없을 경우 null optional 을 리턴한다")
        void failWhenNoProductHasInputId() {
            //when
            Optional<Product> result = productRepository.findDistinctWithOptionsById(1L);

            //then
            assertThat(result).isEmpty();
        }
    }
}
