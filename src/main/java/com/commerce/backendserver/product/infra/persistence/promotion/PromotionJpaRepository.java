package com.commerce.backendserver.product.infra.persistence.promotion;

import com.commerce.backendserver.product.domain.promotion.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionJpaRepository extends JpaRepository<Promotion, Long> {
}
