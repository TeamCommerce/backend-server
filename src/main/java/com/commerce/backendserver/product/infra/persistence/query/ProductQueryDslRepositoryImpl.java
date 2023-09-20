package com.commerce.backendserver.product.infra.persistence.query;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryDslRepositoryImpl implements ProductQueryDslRepository {
}
