package com.commerce.backendserver.product.domain.persistence;

import com.commerce.backendserver.product.domain.infra.query.ProductQueryDslRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProductQueryRepository extends ProductJpaRepository, ProductQueryDslRepository {
}
