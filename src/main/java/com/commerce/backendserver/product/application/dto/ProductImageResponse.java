package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.image.domain.ProductImage;

import java.util.List;

public record ProductImageResponse(
        List<String> urls
) {
    public static List<String> toResponse(List<ProductImage> images) {
        return images.stream()
                .map(i -> String.valueOf(i.getUrl()))
                .toList();
    }
}
