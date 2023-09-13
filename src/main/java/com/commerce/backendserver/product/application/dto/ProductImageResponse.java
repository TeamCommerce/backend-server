package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.image.domain.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public final class ProductImageResponse {
    private final List<String> urls;

    public static List<String> toResponse(List<ProductImage> images) {
        return images.stream()
                .map(i -> String.valueOf(i.getUrl()))
                .toList();
    }
}
