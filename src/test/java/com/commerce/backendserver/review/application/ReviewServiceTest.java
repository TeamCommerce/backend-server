package com.commerce.backendserver.review.application;

import static com.commerce.backendserver.common.fixture.ReviewFixture.*;
import static com.commerce.backendserver.global.exception.error.GlobalError.*;
import static com.commerce.backendserver.product.fixture.ProductFixture.*;
import static com.commerce.backendserver.review.exception.ReviewError.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.util.ReflectionTestUtils.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.commerce.backendserver.common.base.MockTestBase;
import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.image.stub.ImageServiceStub;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductRepository;
import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.review.application.dto.request.CreateReviewRequest;
import com.commerce.backendserver.review.stub.ReviewRepositoryStub;

@DisplayName("[ReviewService Test] - Application layer")
class ReviewServiceTest extends MockTestBase {

	private final ReviewService reviewService;

	private final ProductRepository productRepository;

	public ReviewServiceTest(
		@Mock final ProductRepository productRepository
	) {
		this.productRepository = productRepository;
		reviewService = new ReviewService(new ReviewRepositoryStub(), new ImageServiceStub(), productRepository);
	}

	@Nested
	@DisplayName("[createReview]")
	class createReview {

		private CreateReviewRequest request;

		@BeforeEach
		void setUp() throws IOException {
			//given
			request = A.toCreateRequest();
		}

		@Test
		@DisplayName("[success]")
		void success() {
			//given
			given(productRepository.findDistinctWithOptionsById(1L))
				.willReturn(generateProductOf(
					product -> setProductOptionsId(product.getOptions(), request.productOptionId()))
				);

			//when
			Long result = reviewService.createReview(request, 1L);

			//then
			assertThat(result).isEqualTo(1L);
		}

		@Test
		@DisplayName("[Fail] 해당 Id의 상품이 존재하지 않아 실패")
		void failWhenProductNotFound() {
			//given
			given(productRepository.findDistinctWithOptionsById(1L))
				.willReturn(generateProductEmpty());

			//when, then
			assertThatThrownBy(() -> reviewService.createReview(request, 1L))
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(GLOBAL_NOT_FOUND.getMessage());
		}

		@Test
		@DisplayName("[Fail] 입력한 ProductOptionId 가 입력한 Product 의 옵션에 존재하지 않아 실패한다")
		void failWhenNoProductOptionIdInProduct() {
			//given
			final Long invalidProductOptionId = 1000L;
			given(productRepository.findDistinctWithOptionsById(1L))
				.willReturn(generateProductOf(
					product -> setProductOptionsId(product.getOptions(), invalidProductOptionId))
				);

			//when
			ThrowingCallable throwingCallable = () -> reviewService.createReview(request, 1L);

			//then
			assertThatThrownBy(throwingCallable)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(NOT_MATCH_PRODUCT_OPTION_ID.getMessage());
		}
	}

	private Optional<Product> generateProductOf(final Consumer<Product> productOptionManager) {
		Product product = VALID_PRODUCT.toEntity(null);
		productOptionManager.accept(product);

		return Optional.of(product);
	}

	private Optional<Product> generateProductEmpty() {
		return Optional.empty();
	}

	private void setProductOptionsId(final List<ProductOption> options, final Long id) {
		for (int i = 0; i < options.size(); i++) {
			setField(options.get(i), "id", i + id);
		}
	}
}
