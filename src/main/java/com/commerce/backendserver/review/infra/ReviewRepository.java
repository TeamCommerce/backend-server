package com.commerce.backendserver.review.infra;

import com.commerce.backendserver.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
