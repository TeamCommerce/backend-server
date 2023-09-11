package com.commerce.backendserver.product.domain.promotion;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;
import com.commerce.backendserver.product.domain.promotion.discount.PromotionPriceCalculator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.commerce.backendserver.product.exception.ProductError.MINUS_APPLIED_PROMOTION_PRICE;
import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class PromotionDiscountAttribute {

    @Enumerated(value = STRING)
    @Column(
            nullable = false,
            columnDefinition = "varchar(100)")
    private PromotionType type;

    @Column(nullable = false)
    private Integer discountAmount;

}
