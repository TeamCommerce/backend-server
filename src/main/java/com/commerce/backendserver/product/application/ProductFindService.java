package com.commerce.backendserver.product.application;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.application.dto.ProductSingleSimpleResponse;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.option.ProductOption;
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

    public Product findSingleProductInfo(Long id) {
        Product product = productQueryRepository.findProductInfoById(id)
                .orElseThrow(() -> CommerceException.of(PRODUCT_NOT_FOUND));
        ProductOption productOption = product.getOptions().get(0);
        return product;
    }

    public ProductSingleSimpleResponse toSingleSimpleResponse(Product product) {
        return ProductSingleSimpleResponse.builder()
                .id(product.getId())
                .images(product.getImages())
                .info(product.getInfo())
                .attribute(product.getPriceAttribute())
                .build();
    }
}
