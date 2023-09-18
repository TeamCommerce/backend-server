package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;

import java.util.List;

public record ProductSingleSimpleResponse(
        Long id,
        int originPrice,
        PromotionType promotionType,
        int promotionValue,
        int finalPrice,
        String imgUrl,
        List<String> colors,
        ProductBrand brand,
        String name
) {
    public static ProductSingleSimpleResponse from(
            final Product product,
            final int discountedValue,
            final int finalDiscountedPrice,
            final String imgUrl,
            final List<String> colors
    ) {
        return new ProductSingleSimpleResponse(
                product.getId(),
                product.getPriceAttribute().getOriginPrice(),
                product.getPriceAttribute().getPromotion().getDiscountAttribute().getType(),
                discountedValue,
                finalDiscountedPrice,
                imgUrl,
                colors,
                product.getCommonInfo().getBrand(),
                product.getCommonInfo().getName()
        );

    }
}
