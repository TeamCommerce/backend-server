package com.commerce.backendserver.product.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository {

	// Query
	Optional<Product> findProductInfoById(Long id);

	List<Product> findBestProducts();

	Optional<Product> findById(Long id);

	Optional<Product> findDistinctWithOptionsById(Long id);

	// Command
	Product save(Product product);
}
