package com.commerce.backendserver.product.infra.persistence.query;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryDslRepositoryImpl implements ProductQueryDslRepository {
}
