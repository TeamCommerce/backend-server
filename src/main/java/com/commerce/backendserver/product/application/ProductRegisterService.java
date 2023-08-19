package com.commerce.backendserver.product.application;

import com.commerce.backendserver.product.domain.ProductCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductRegisterService {

    private final ProductCommandRepository productCommandRepository;
}
