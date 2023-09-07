package com.commerce.backendserver.product.application;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.application.dto.ProductSingleSimpleResponse;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.persistence.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.commerce.backendserver.product.application.dto.ProductImageResponse.convertToImageResponseList;
import static com.commerce.backendserver.product.application.dto.ProductOptionResponse.convertToOptionResponseList;
import static com.commerce.backendserver.product.exception.ProductError.PRODUCT_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductFindService {

    private final ProductQueryRepository productQueryRepository;

    public Product findSingleProductInfo(Long id) {
        return productQueryRepository.findProductInfoById(id)
                .orElseThrow(() -> CommerceException.of(PRODUCT_NOT_FOUND));
    }

    public ProductSingleSimpleResponse toSingleSimpleResponse(Product product) {
        return ProductSingleSimpleResponse.builder()
                .id(product.getId())
                .options(convertToOptionResponseList(product.getOptions()))
                .images(convertToImageResponseList(product.getImages()))
                .promotionDiscountedAmount(0) // todo Apply Discount Policy
                .originPrice(product.getProductPriceAttribute().getOriginPrice())
                .promotionType(product.getProductPriceAttribute().getPromotion().getPromotionPriceAttribute().getType())
                .appliedPromotionPrice(product.getProductPriceAttribute().getOriginPrice())
                .build();
    }
}
