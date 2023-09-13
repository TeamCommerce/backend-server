package com.commerce.backendserver.product.domain.persistence;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductCommandRepository extends ProductJpaRepository {
}
