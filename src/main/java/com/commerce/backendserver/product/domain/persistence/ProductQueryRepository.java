package com.commerce.backendserver.product.domain.persistence;

import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.infra.query.ProductQueryDslRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface ProductQueryRepository extends ProductJpaRepository, ProductQueryDslRepository {

    // Single Product Find Service
    @Query("select distinct p from Product p " +
            "left join fetch p.images " +
            "where p.id = :id")
    Optional<Product> findProductInfoById(@Param("id") Long id);
}
