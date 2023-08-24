package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.commerce.backendserver.product.exception.ProductError.INVALID_PRICE_ATTRIBUTE;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ProductPriceAttribute {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(nullable = false, columnDefinition = "int unsigned")
    private Integer originPrice;

    //== Constructor Method ==//
    @Builder
    private ProductPriceAttribute(
            final Promotion promotion,
            final Integer originPrice
    ) {
        validatePrice(originPrice);
        this.promotion = promotion;
        this.originPrice = originPrice;
    }

    //== Static Factory Method ==//
    public static ProductPriceAttribute of(
            final Promotion promotion,
            final Integer originPrice
    ) {
        return ProductPriceAttribute.builder()
                .promotion(promotion)
                .originPrice(originPrice)
                .build();
    }

    private void validatePrice(Integer originPrice) {
        if (originPrice < 0) {
            throw CommerceException.of(INVALID_PRICE_ATTRIBUTE);
        }
    }
}
