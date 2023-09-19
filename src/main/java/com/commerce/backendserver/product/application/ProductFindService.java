package com.commerce.backendserver.product.application;

import com.commerce.backendserver.global.dto.response.ResponseWrapper;
import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.application.dto.ProductSingleDetailResponse;
import com.commerce.backendserver.product.application.dto.ProductSimpleResponse;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;
import com.commerce.backendserver.product.infra.persistence.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.commerce.backendserver.product.application.dto.ProductResponseAssembler.toWrappedSimpleResponses;
import static com.commerce.backendserver.product.application.dto.ProductSimpleResponse.from;
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

    public ProductSimpleResponse toSingleSimpleResponse(
            Long id
    ) {
        Product product = productQueryRepository.findProductInfoById(id)
                .orElseThrow(() -> CommerceException.of(PRODUCT_NOT_FOUND));

        ProductPriceAttribute priceAttribute = product.getPriceAttribute();
        int promotionDiscountedValue = priceAttribute.getPromotionDiscountedValue();
        int finalDiscountedPrice = priceAttribute.applyPromotionDiscount();
        List<String> colors = product.getDistinctColors();

        String imgUrl = product.getMainImageUrl();

        return from(
                product,
                promotionDiscountedValue,
                finalDiscountedPrice,
                imgUrl,
                colors
        );
    }

    public ResponseWrapper<ProductSimpleResponse> toBestProductsResponse() {
        List<Product> products = productQueryRepository.findBestProducts();
        validateProducts(products);
        return toWrappedSimpleResponses(products);
    }

    private void validateProducts(List<Product> products) {
        if (products.isEmpty()) {
            throw CommerceException.of(PRODUCT_NOT_FOUND);
        }
    }
}

