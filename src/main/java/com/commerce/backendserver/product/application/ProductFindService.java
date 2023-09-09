package com.commerce.backendserver.product.application;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.application.dto.ProductSingleSimpleResponse;
import com.commerce.backendserver.product.application.price.ProductPriceService;
import com.commerce.backendserver.product.domain.Product;
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
    private final ProductPriceService productPriceService;

    public ProductSingleSimpleResponse toSingleSimpleResponse(Long id) {

        Product product = productQueryRepository.findProductInfoById(id)
                .orElseThrow(() -> CommerceException.of(PRODUCT_NOT_FOUND));

        int finalDiscountedPrice = productPriceService.applyPromotionDiscount(product);
        int discountedValue = productPriceService.getPromotionDiscountedValue(product, product.getPriceAttribute().getOriginPrice());

        return ProductSingleSimpleResponse.from(
                product,
                finalDiscountedPrice,
                discountedValue);
    }
}
