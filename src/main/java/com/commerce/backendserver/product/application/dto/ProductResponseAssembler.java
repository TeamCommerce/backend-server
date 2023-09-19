package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.global.dto.response.ResponseWrapper;
import com.commerce.backendserver.product.domain.Product;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.commerce.backendserver.product.application.dto.ProductSimpleResponse.from;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProductResponseAssembler {

    public static ResponseWrapper<ProductSimpleResponse> toWrappedSimpleResponses(
            List<Product> products
    ) {
        List<ProductSimpleResponse> response =
                products.stream()
                        .map(product ->
                                ProductSimpleResponse.from(
                                product,
                                product.getPriceAttribute().getPromotionDiscountedValue(),
                                product.getPriceAttribute().applyPromotionDiscount(),
                                product.getMainImageUrl(),
                                product.getDistinctColors())
                        )
                        .toList();
        return new ResponseWrapper<>(response);
    }

}
