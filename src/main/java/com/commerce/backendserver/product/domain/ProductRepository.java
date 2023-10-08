package com.commerce.backendserver.product.domain;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {

    // Query
    Optional<Product> findProductInfoById(@Param("id") Long id);

    List<Product> findBestProducts();

    Optional<Product> findDistinctWithOptionsById(Long id);

    // Command
    Product save(Product product);
}
