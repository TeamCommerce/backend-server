package com.commerce.backendserver.product.application.dto.response;

import com.commerce.backendserver.product.domain.Product;

import java.util.List;

public record ProductSimpleResponse(
        ProductCommonResponse product,
        String mainImage,
        List<String> colors
) {
    public static ProductSimpleResponse from(Product product) {
        return new ProductSimpleResponse(
                ProductCommonResponse.from(product),
                product.getMainImageUrl(),
                product.getDistinctColors()
        );
    }
}
