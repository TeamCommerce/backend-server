package com.commerce.backendserver.product.application;

import com.commerce.backendserver.product.domain.ProductCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductCommandRepository productCommandRepository;
}
