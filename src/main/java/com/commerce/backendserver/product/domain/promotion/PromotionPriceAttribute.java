package com.commerce.backendserver.product.domain.promotion;

import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class PromotionPriceAttribute {

    @Enumerated(value = STRING)
    @Column(
            nullable = false,
            columnDefinition = "varchar(100)")
    private PromotionType type;

    @Column(nullable = false)
    private Integer discountAmount;
}
