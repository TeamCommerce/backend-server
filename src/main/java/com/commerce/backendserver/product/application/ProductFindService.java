package com.commerce.backendserver.product.application;

import com.commerce.backendserver.global.dto.response.ResponseWrapper;
import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.application.dto.response.ProductDetailResponse;
import com.commerce.backendserver.product.application.dto.response.ProductResponseAssembler;
import com.commerce.backendserver.product.application.dto.response.ProductSimpleResponse;
import com.commerce.backendserver.product.domain.Product;
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

    public ProductDetailResponse toSingleDetailResponse(Long id) {
        Product product = productQueryRepository.findProductInfoById(id)
                .orElseThrow(() -> CommerceException.of(PRODUCT_NOT_FOUND));

        return ProductResponseAssembler.transferToDetailResponse(product);
    }

    public ResponseWrapper<ProductSimpleResponse> toBestProductsResponse() {
        List<Product> bestProducts = productQueryRepository.findBestProducts();
        validateProducts(bestProducts);

        return ProductResponseAssembler.wrapSimpleResponses(bestProducts);
    }

    //== Validation Method ==//
    private void validateProducts(List<Product> products) {
        if (products.isEmpty()) {
            throw CommerceException.of(PRODUCT_NOT_FOUND);
        }
    }
}

