package com.commerce.backendserver.product.infra.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdaptor implements ProductRepository {

	private final ProductQueryRepository productQueryRepository;
	private final ProductCommandRepository productCommandRepository;

	//== Query Repository ==//
	@Override
	public Optional<Product> findProductInfoById(Long id) {
		return productQueryRepository.findProductInfoById(id);
	}

	@Override
	public List<Product> findBestProducts() {
		return productQueryRepository.findBestProducts();
	}

	@Override
	public Optional<Product> findDistinctWithOptionsById(Long id) {
		return productQueryRepository.findDistinctWithOptionsById(id);
	}

	@Override
	public Optional<Product> findById(Long id) {
		return productQueryRepository.findById(id);
	}

	//== Command Repository ==//
	@Override
	public Product save(Product product) {
		return productCommandRepository.save(product);
	}
}
