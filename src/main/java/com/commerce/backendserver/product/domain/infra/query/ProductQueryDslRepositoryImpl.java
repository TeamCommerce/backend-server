package com.commerce.backendserver.product.domain.infra.query;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryDslRepositoryImpl implements ProductQueryDslRepository {

}
