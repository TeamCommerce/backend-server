package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductSingleSimpleResponse(
        Long id,
        Integer originPrice,
        PromotionType promotionType,
        Integer promotionValue,
        Integer promotionDiscountedAmount,
        Integer appliedPromotionPrice,
        List<String> images,
        List<ProductOptionResponse> options
) {

}
