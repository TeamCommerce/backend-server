package com.commerce.backendserver.review.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
