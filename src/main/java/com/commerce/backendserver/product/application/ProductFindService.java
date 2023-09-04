package com.commerce.backendserver.product.application;

import com.commerce.backendserver.product.domain.persistence.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFindService {

    private final ProductQueryRepository productQueryRepository;
}
