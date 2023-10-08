package com.commerce.backendserver.review.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.backendserver.review.domain.Review;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
}
