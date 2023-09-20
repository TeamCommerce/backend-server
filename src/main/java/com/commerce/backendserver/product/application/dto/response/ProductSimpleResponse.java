package com.commerce.backendserver.product.application.dto.response;

import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;

import java.util.List;

public record ProductSimpleResponse(
        Long id,
        String name,
        ProductBrand brand,
        String description,
        Integer originPrice,
        PromotionType promotionType,
        Integer promotionValue,
        Integer finalPrice,
        List<String> colors,
        String mainImage
) {
}
