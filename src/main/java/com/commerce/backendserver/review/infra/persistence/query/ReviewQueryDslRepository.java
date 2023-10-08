package com.commerce.backendserver.review.infra.persistence.query;

import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.review.domain.Review;

import java.util.List;
import java.util.Set;

public interface ReviewQueryDslRepository {
    List<Review> findReviewByStatisticCondition(
            final Set<String> engColorNames,
            final Set<ProductSize> sizes,
            final Set<String> additionalOptions,
            final Set<Integer> scores,
            final Set<String> additionalInfoList
    );
}
