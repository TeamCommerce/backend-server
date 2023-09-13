package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import com.commerce.backendserver.product.domain.promotion.PromotionDiscountAttribute;
import com.commerce.backendserver.product.domain.promotion.discount.PromotionPriceCalculator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.commerce.backendserver.product.exception.ProductError.INVALID_PRICE_ATTRIBUTE;
import static com.commerce.backendserver.product.exception.ProductError.MINUS_APPLIED_PROMOTION_PRICE;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ProductPriceAttribute {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(nullable = false)
    private Integer originPrice;

    //== Constructor Method ==//
    @Builder(access = PRIVATE)
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

    //== Business Method ==//
    public int applyPromotionDiscount() {
        int productDiscountedPrice = this.originPrice - this.getPromotionDiscountedValue();
        validateProductDiscountedPrice(productDiscountedPrice);
        return productDiscountedPrice;
    }

    public int getPromotionDiscountedValue() {
        PromotionPriceCalculator discountTypeCalculator = this.getPromotionPriceAttribute().getType().getCalculator();
        return discountTypeCalculator.getPromotionDiscountedAmount(this.getPromotionPriceAttribute());
    }

    //== Validate Method ==//
    private void validatePrice(Integer originPrice) {
        if (originPrice < 0) {
            throw CommerceException.of(INVALID_PRICE_ATTRIBUTE);
        }
    }

    private void validateProductDiscountedPrice(int productDiscountedPrice) {
        if (productDiscountedPrice < 0) {
            throw CommerceException.of(MINUS_APPLIED_PROMOTION_PRICE);
        }
    }

    //== Utility Method ==//
    private PromotionDiscountAttribute getPromotionPriceAttribute() {
        return this.getPromotion().getDiscountAttribute();
    }
}
