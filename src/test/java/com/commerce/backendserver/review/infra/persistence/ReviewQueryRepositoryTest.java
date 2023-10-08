package com.commerce.backendserver.review.infra.persistence;

import static com.commerce.backendserver.product.domain.option.constants.ProductSize.*;
import static com.commerce.backendserver.product.fixture.ProductFixture.*;
import static com.commerce.backendserver.product.fixture.PromotionFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.commerce.backendserver.common.base.RepositoryTestBase;
import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import com.commerce.backendserver.product.infra.persistence.ProductCommandRepository;
import com.commerce.backendserver.product.infra.persistence.promotion.PromotionCommandRepository;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfo;

/**
 * ReviewFixture, Product 도메인 관련 Fixture 의 데이터가 바뀌면 테스트가 실패할 수 있음!! 주의!!
 */
@DisplayName("[ReviewQueryRepository Test] - Infra layer")
class ReviewQueryRepositoryTest extends RepositoryTestBase {

	private final Set<Review> reviews = new HashSet<>();
	@Autowired
	private ReviewQueryRepository reviewQueryRepository;
	@Autowired
	private ProductCommandRepository productRepository;
	@Autowired
	private PromotionCommandRepository promotionRepository;
	private List<Product> products;

	@BeforeEach
	void setUp() {
		Promotion promotion = promotionRepository.save(VALID_RATE_PROMOTION.toEntity());

		products = List.of(
			productRepository.save(VALID_PRODUCT.toEntity(promotion)),
			productRepository.save(VALID_PRODUCT_2.toEntity(promotion)),
			productRepository.save(VALID_PRODUCT_3.toEntity(promotion))
		);

		ReviewFixture[] reviewFixtures = ReviewFixture.values();
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			Long optionId = product.getOptions().get(0).getId();

			reviews.add(reviewQueryRepository.save(reviewFixtures[i].toEntity(product, 1L, optionId)));
		}
	}

	private List<Review> fetchExpectedReviews(List<Long> expectedOptionIds) {
		return reviews.stream()
			.filter(review -> expectedOptionIds.contains(review.getProductOptionId()))
			.toList();
	}

	private List<Long> generateExpectedOptionIds(
		final Predicate<ProductOption> hasConditionCommand
	) {
		return products.stream()
			.map(Product::getOptions)
			.flatMap(List::stream)
			.filter(hasConditionCommand)
			.map(ProductOption::getId)
			.toList();
	}

	@Nested
	@DisplayName("[findReviewByStatisticCondition]")
	class findReviewByStatisticConditionTest {

		@Test
		@DisplayName("[Success] 조건이 없을때 모든 리뷰를 조회한다")
		void successWhenNoCondition() {
			//when
			List<Review> result = reviewQueryRepository.findReviewByStatisticCondition(
				null, null, null, null, null
			);

			//then
			assertThat(result).containsAll(reviews);
		}

		@Test
		@DisplayName("[Success] color 에 대한 조건에 맞는 리뷰를 조회한다")
		void successWhenHasColorCondition() {
			//given
			Set<String> engColorNames = Set.of("blue", "red");

			//when
			List<Review> result = reviewQueryRepository.findReviewByStatisticCondition(
				engColorNames, null, null, null, null
			);

			//then
			assertQueryResultByProductCondition(
				option -> engColorNames.contains(option.getColor().getEngColorName()),
				result
			);
		}

		@Test
		@DisplayName("[Success] size 에 대한 조건에 맞는 리뷰를 조회한다")
		void successWhenHasSizeCondition() {
			//given
			Set<ProductSize> sizes = Set.of(TWO_XS, L);

			//when
			List<Review> result = reviewQueryRepository.findReviewByStatisticCondition(
				null, sizes, null, null, null
			);

			//then
			assertQueryResultByProductCondition(
				option -> sizes.contains(option.getSize()),
				result
			);
		}

		@Test
		@DisplayName("[Success] additionalOption 에 대한 조건에 맞는 리뷰를 조회한다")
		void successWhenHasAdditionalOptionCondition() {
			//given
			Set<String> additionalOptionValues = Set.of("O");

			//when
			List<Review> result = reviewQueryRepository.findReviewByStatisticCondition(
				null, null, additionalOptionValues, null, null
			);

			//then
			assertQueryResultByProductCondition(
				option -> additionalOptionValues.contains(option.getAdditionalOption().getValue()),
				result
			);
		}

		@Test
		@DisplayName("[Success] score 에 대한 조건에 맞는 리뷰를 조회한다")
		void successWhenHasScoreCondition() {
			//given
			Set<Integer> scores = Set.of(4, 2);

			//when
			List<Review> result = reviewQueryRepository.findReviewByStatisticCondition(
				null, null, null, scores, null
			);

			//then
			assertQueryResultByReviewCondition(
				review -> scores.contains(review.getScore()),
				result
			);
		}

		@Test
		@DisplayName("[Success] additionalInfo 에 대한 조건에 맞는 리뷰를 조회한다")
		void successWhenHasAdditionalInfoCondition() {
			//given
			Set<String> additionalInfoValues = Set.of("Large", "Small");

			//when
			List<Review> result = reviewQueryRepository.findReviewByStatisticCondition(
				null, null, null, null, additionalInfoValues
			);

			//then
			assertQueryResultByReviewCondition(
				review -> {
					List<String> targetInfoValues = review.getAdditionalInfoList().stream()
						.map(AdditionalInfo::getInfoValue).toList();
					return targetInfoValues.stream().anyMatch(additionalInfoValues::contains);
				},
				result
			);
		}

		private void assertQueryResultByProductCondition(
			final Predicate<ProductOption> hasConditionCommand,
			final List<Review> result
		) {
			List<Long> expectedOptionIds = generateExpectedOptionIds(hasConditionCommand);

			List<Review> expectedReviews = fetchExpectedReviews(expectedOptionIds);

			assertThat(result).hasSameSizeAs(expectedReviews);
			assertThat(result).containsAll(expectedReviews);
		}

		private void assertQueryResultByReviewCondition(
			final Predicate<Review> hasConditionCommand,
			final List<Review> result
		) {
			List<Review> expectedReviews = reviews.stream()
				.filter(hasConditionCommand)
				.toList();

			assertThat(result).hasSameSizeAs(expectedReviews);
			assertThat(result).containsAll(expectedReviews);
		}
	}
}
