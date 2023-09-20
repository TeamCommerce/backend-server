package com.commerce.backendserver.product.infra.persistence.promotion;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PromotionCommandRepository extends PromotionJpaRepository {
}
