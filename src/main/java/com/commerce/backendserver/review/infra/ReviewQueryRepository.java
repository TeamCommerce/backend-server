package com.commerce.backendserver.review.infra;

import com.commerce.backendserver.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.backendserver.review.infra.persistence.ReviewQueryDslRepository;

@Transactional(readOnly = true)
public interface ReviewQueryRepository extends JpaRepository<Review, Long>, ReviewQueryDslRepository {

}
