package com.commerce.backendserver.product.application;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.commerce.backendserver.product.exception.ProductError.NOT_FOUND_PRODUCT;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductFindService {

    private final ProductQueryRepository productQueryRepository;

    public Product findProductById(Long productId) {
        return productQueryRepository.findById(productId)
                .orElseThrow(() -> CommerceException.of(NOT_FOUND_PRODUCT));
    }
}
