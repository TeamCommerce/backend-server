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

import static com.commerce.backendserver.product.exception.ProductError.*;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ProductPriceAttribute {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(columnDefinition = "int unsigned")
    private Integer originPrice;

    //== Constructor Method ==//
    @Builder
    private ProductPriceAttribute(
            final Promotion promotion,
            final Integer originPrice
    ) {
        validatePromotion(promotion);
        validateOriginPrice(originPrice);
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

    //== Validation Method ==//
    private void validatePromotion(final Promotion promotion) {
        if (isNull(promotion)) {
            throw CommerceException.of(INVALID_PROMOTION);
        }
    }

    private void validateOriginPrice(final Integer originPrice) {
        if (isNull(originPrice)) {
            throw CommerceException.of(INVALID_ORIGIN_PRICE);
        } else if (originPrice < 0) {
            throw CommerceException.of(MINUS_ORIGIN_PRICE);
        }
    }
}
