package com.commerce.backendserver.product.infra.persistence.promotion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.backendserver.product.domain.promotion.Promotion;

public interface PromotionJpaRepository extends JpaRepository<Promotion, Long> {
}
