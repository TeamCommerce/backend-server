package com.commerce.backendserver.product.domain.persistence;

import com.commerce.backendserver.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
