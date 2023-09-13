package com.commerce.backendserver.product.application.price;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.application.price.promotion.ApplyPromotionFactory;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.commerce.backendserver.product.exception.ProductError.MINUS_APPLIED_PROMOTION_PRICE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductPriceService {

    private final ApplyPromotionFactory factory;

    public Integer applyPromotionDiscount(
            Product product
    ) {
        int originPrice = product.getProductPriceAttribute().getOriginPrice();
        int discountedValue = getPromotionDiscountedValue(product, originPrice);
        int finalDiscountedPrice = originPrice - discountedValue;
        if (finalDiscountedPrice < 0) {
            throw CommerceException.of(MINUS_APPLIED_PROMOTION_PRICE);
        } else {
            return finalDiscountedPrice;
        }
    }

    public int getPromotionDiscountedValue(Product product, int originPrice) {
        Promotion promotion = product.getProductPriceAttribute().getPromotion();
        PromotionType type = promotion.getPromotionPriceAttribute().getType();

        return factory.calculateDiscountedAmount(type)
                .getAppliedPromotionAmount(originPrice, promotion);
    }

}
