package com.commerce.backendserver.product.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.backendserver.product.domain.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}

