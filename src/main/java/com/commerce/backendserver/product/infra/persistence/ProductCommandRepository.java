package com.commerce.backendserver.product.infra.persistence;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductCommandRepository extends ProductJpaRepository {
}
