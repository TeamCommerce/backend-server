package com.commerce.backendserver.product.infra.persistence;

import com.commerce.backendserver.product.infra.persistence.ProductQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryDslRepositoryImpl implements ProductQueryDslRepository {

}
