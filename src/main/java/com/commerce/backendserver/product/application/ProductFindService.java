package com.commerce.backendserver.product.application;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.application.dto.ProductSingleDetailResponse;
import com.commerce.backendserver.product.application.dto.ProductSingleSimpleResponse;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;
import com.commerce.backendserver.product.infra.persistence.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.commerce.backendserver.product.exception.ProductError.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductFindService {

    private final ProductQueryRepository productQueryRepository;

    public ProductSingleDetailResponse toSingleDetailResponse(
            Long id
    ) {
        Product product = productQueryRepository.findProductInfoById(id)
                .orElseThrow(() -> CommerceException.of(PRODUCT_NOT_FOUND));

        ProductPriceAttribute priceAttribute = product.getPriceAttribute();
        int promotionDiscountedValue = priceAttribute.getPromotionDiscountedValue();
        int finalDiscountedPrice = priceAttribute.applyPromotionDiscount();

        return ProductSingleDetailResponse.
                from(
                        product,
                        promotionDiscountedValue,
                        finalDiscountedPrice);
    }

    public ProductSingleSimpleResponse toSingleSimpleResponse(
            Long id
    ) {
        Product product = productQueryRepository.findProductInfoById(id)
                .orElseThrow(() -> CommerceException.of(PRODUCT_NOT_FOUND));

        ProductPriceAttribute priceAttribute = product.getPriceAttribute();
        int promotionDiscountedValue = priceAttribute.getPromotionDiscountedValue();
        int finalDiscountedPrice = priceAttribute.applyPromotionDiscount();
        List<String> colors = product.getDistinctColors();

        String imgUrl = product.getMainImageUrl();

        return ProductSingleSimpleResponse.
                from(
                        product,
                        promotionDiscountedValue,
                        finalDiscountedPrice,
                        imgUrl,
                        colors
                );
    }
}
