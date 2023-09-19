package com.commerce.backendserver.product.application.dto.response;

import com.commerce.backendserver.product.domain.Product;

import java.util.List;

public record ProductDetailResponse(
        ProductCommonResponse product,
        List<String> images,
        List<ProductOptionResponse> options
) {
    public static ProductDetailResponse from(
            final Product product
    ) {
        return new ProductDetailResponse(
                ProductCommonResponse.from(product),
                ProductResponseAssembler.toImageResponseList(product.getImages()),
                ProductOptionResponse.toOptionResponseList(product.getOptions()));
    }
}
