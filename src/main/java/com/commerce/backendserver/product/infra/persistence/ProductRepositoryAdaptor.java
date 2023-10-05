package com.commerce.backendserver.product.infra.persistence;

import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdaptor implements ProductRepository {

    private final ProductQueryRepository productQueryRepository;
    private final ProductCommandRepository productCommandRepository;

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
    public Product save(Product product) {
        return productCommandRepository.save(product);
    }
}
