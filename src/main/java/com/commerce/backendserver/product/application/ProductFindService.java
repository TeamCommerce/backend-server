package com.commerce.backendserver.product.application;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.image.domain.ProductImage;
import com.commerce.backendserver.product.application.dto.ProductOptionResponse;
import com.commerce.backendserver.product.application.dto.ProductSingleSimpleResponse;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.product.domain.persistence.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.commerce.backendserver.product.exception.ProductError.PRODUCT_NOT_FOUND;
import static java.util.stream.Collectors.toList;

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
                .images(convertToImageResponse(product.getImages()))
                .options(convertToResponseList(product.getOptions()))
                .originPrice(product.getPriceAttribute().getOriginPrice())
                .discountedAmount(0) // todo Apply Discount Policy
                .promotionType(product.getPriceAttribute().getPromotion().getPriceAttribute().getType())
                .finalPrice(product.getPriceAttribute().getOriginPrice())
                .build();
    }

    public List<String> convertToImageResponse(List<ProductImage> images) {
        return images.stream()
                .map(this::convertToImageResponse)
                .collect(toList());
    }

    private String convertToImageResponse(ProductImage image) {
        return String.valueOf(image.getUrl());
    }

    public List<ProductOptionResponse> convertToResponseList(List<ProductOption> productOptions) {
        return productOptions.stream()
                .map(this::convertToResponse)
                .collect(toList());
    }

    // ProductOption을 ProductOptionResponse로 변환하는 메소드
    private ProductOptionResponse convertToResponse(ProductOption productOption) {
        return new ProductOptionResponse(
                productOption.getColor().getColorCode(),
                productOption.getColor().getKorColorName(),
                productOption.getColor().getEngColorName(),
                productOption.getSize(),
                productOption.getOption().getOptionType(),
                productOption.getOption().getOptionValue(),
                productOption.getAdditionalFee(),
                productOption.getInventory(),
                productOption.getProductStatus()
        );
    }
}
