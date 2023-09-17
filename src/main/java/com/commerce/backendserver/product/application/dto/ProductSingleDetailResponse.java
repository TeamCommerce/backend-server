package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;

import java.util.List;

public record ProductSingleDetailResponse(
        Long id,
        String productName,
        String productBrand,
        Integer originPrice,
        PromotionType promotionType,
        Integer promotionValue,
        Integer promotionDiscountedAmount,
        Integer appliedPromotionPrice,
        List<String> images,
        List<ProductOptionResponse> options
) {
    public static ProductSingleDetailResponse from(
            final Product product,
            final int discountedValue,
            final int finalDiscountedPrice
    ) {
        ProductPriceAttribute priceAttribute = product.getPriceAttribute();
        return new ProductSingleDetailResponse(
                product.getId(),
                product.getCommonInfo().getName(),
                product.getCommonInfo().getBrand().toString(),
                priceAttribute.getOriginPrice(),
                priceAttribute.getPromotion().getDiscountAttribute().getType(),
                discountedValue,
                priceAttribute.getPromotion().getDiscountAttribute().getDiscountAmount(),
                finalDiscountedPrice,
                ProductImageResponse.toResponse(product.getImages()),
                ProductOptionResponse.toResponse(product.getOptions()));
    }
}
