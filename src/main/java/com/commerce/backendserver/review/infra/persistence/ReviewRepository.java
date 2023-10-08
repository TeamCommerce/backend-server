package com.commerce.backendserver.review.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.backendserver.review.domain.Review;

@Transactional
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
