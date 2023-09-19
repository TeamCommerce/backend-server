package com.commerce.backendserver.product.application.dto.response;

import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;

import java.util.List;

public record ProductCommonResponse(
        Long id,
        String name,
        ProductBrand brand,
        String description,
        Integer originPrice,
        PromotionType promotionType,
        Integer promotionValue,
        Integer finalPrice,
        List<String> colors
) {
    public static ProductCommonResponse from(Product product) {
        return new ProductCommonResponse(
                product.getId(),
                product.getCommonInfo().getName(),
                product.getCommonInfo().getBrand(),
                product.getCommonInfo().getDescription(),
                product.getPriceAttribute().getOriginPrice(),
                product.getPriceAttribute().getPromotion().getType(),
                product.getPriceAttribute().getPromotion().getPromotionValue(),
                product.getPriceAttribute().applyPromotionDiscount(),
                product.getDistinctColors());
    }
}
