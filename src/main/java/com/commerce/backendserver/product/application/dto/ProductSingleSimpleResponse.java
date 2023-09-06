package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.image.domain.ProductImage;
import com.commerce.backendserver.product.domain.ProductCommonInfo;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;
import com.commerce.backendserver.product.domain.option.ProductOption;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductSingleSimpleResponse(
        Long id,
        List<ProductImage> images,
        List<ProductOption> options,
        ProductCommonInfo info,
        ProductPriceAttribute attribute
) {

}
