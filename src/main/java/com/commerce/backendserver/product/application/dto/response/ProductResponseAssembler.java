package com.commerce.backendserver.product.application.dto.response;

import com.commerce.backendserver.global.dto.response.ResponseWrapper;
import com.commerce.backendserver.image.domain.ProductImage;
import com.commerce.backendserver.product.domain.Product;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProductResponseAssembler {

    //== Wrapper Method ==//
    public static ResponseWrapper<ProductSimpleResponse> toProductSimpleResponseList(List<Product> products) {
        List<ProductSimpleResponse> response = transferToSimpleResponses(products);
        return new ResponseWrapper<>(response);
    }

    //== Transfer Method ==//
    public static List<String> toImageResponseList(List<ProductImage> images) {
        return images.stream()
                .map(image -> String.valueOf(image.getUrl()))
                .toList();
    }

    private static List<ProductSimpleResponse> transferToSimpleResponses(List<Product> products) {
        return products.stream()
                .map(ProductSimpleResponse::from).toList();
    }
}
