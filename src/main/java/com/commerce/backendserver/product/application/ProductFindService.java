package com.commerce.backendserver.product.application;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.commerce.backendserver.global.exception.error.GlobalError.GLOBAL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductFindService {

    private final ProductQueryRepository productQueryRepository;

    public Product findProductById(Long productId) {
        return productQueryRepository.findById(productId)
                .orElseThrow(() -> CommerceException.of(GLOBAL_NOT_FOUND));
    }
}
