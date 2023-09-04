package com.commerce.backendserver.product.domain.persistence;

import com.commerce.backendserver.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductCommandRepository extends ProductJpaRepository {
}
