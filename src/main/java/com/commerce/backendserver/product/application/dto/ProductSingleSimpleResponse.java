package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductSingleSimpleResponse {

    private final Long id;
    private final String productName;
    private final String productBrand;
    private final Integer originPrice;
    private final PromotionType promotionType;
    private final Integer promotionValue;
    private final Integer promotionDiscountedAmount;
    private final Integer appliedPromotionPrice;
    private final List<String> images;
    private final List<ProductOptionResponse> options;

    //== Static Factory Method ==//
    public static ProductSingleSimpleResponse of(
            final Long id,
            final String productName,
            final String productBrand,
            final Integer originPrice,
            final PromotionType promotionType,
            final Integer promotionValue,
            final Integer promotionDiscountedAmount,
            final Integer appliedPromotionPrice,
            final List<String> images,
            final List<ProductOptionResponse> options
    ) {
        return ProductSingleSimpleResponse.builder()
                .id(id)
                .productName(productName)
                .productBrand(productBrand)
                .originPrice(originPrice)
                .promotionType(promotionType)
                .promotionValue(promotionValue)
                .promotionDiscountedAmount(promotionDiscountedAmount)
                .appliedPromotionPrice(appliedPromotionPrice)
                .images(images)
                .options(options)
                .build();
    }

    public static ProductSingleSimpleResponse from(
            final Product product,
            final int discountedValue,
            final int finalDiscountedPrice
    ) {
        ProductPriceAttribute priceAttribute = product.getPriceAttribute();
        return ProductSingleSimpleResponse.of(
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
