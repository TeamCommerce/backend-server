package com.commerce.backendserver.product.application;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.application.dto.ProductSingleDetailResponse;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;
import com.commerce.backendserver.product.domain.persistence.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        int promotionalPrice = priceAttribute.applyPromotionDiscount();

        return ProductSingleDetailResponse.
                from(
                        product,
                        promotionDiscountedValue,
                        promotionalPrice);
    }
}
