package com.commerce.backendserver.product.domain.promotion;

import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class PromotionDiscountAttribute {

    @Enumerated(value = STRING)
    @Column(
            nullable = false,
            columnDefinition = "varchar(100)")
    private PromotionType type;

    @Column(nullable = false)
    private Integer discountAmount;

    //== Static Factory Method ==//
    public static PromotionDiscountAttribute of(
            final PromotionType type,
            final Integer discountAmount
    ) {
        return PromotionDiscountAttribute.builder()
                .type(type)
                .discountAmount(discountAmount)
                .build();
    }
}
