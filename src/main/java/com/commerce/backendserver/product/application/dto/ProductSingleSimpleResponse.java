package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductSingleSimpleResponse(
        Long id,
        List<ProductOptionResponse> options,
        List<String> images,
        Integer originPrice,
        PromotionType promotionType,
        Integer promotionDiscountedAmount,
        Integer appliedPromotionPrice
) {

}
