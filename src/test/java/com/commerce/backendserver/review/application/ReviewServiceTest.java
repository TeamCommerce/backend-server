package com.commerce.backendserver.review.application;

import com.commerce.backendserver.common.base.MockTestBase;
import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.image.application.ImageService;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.infra.persistence.ProductQueryRepository;
import com.commerce.backendserver.review.application.dto.request.CreateReviewRequest;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.infra.persistence.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.commerce.backendserver.common.fixture.ReviewFixture.A;
import static com.commerce.backendserver.global.exception.error.GlobalError.GLOBAL_NOT_FOUND;
import static com.commerce.backendserver.product.fixture.ProductFixture.VALID_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@DisplayName("[ReviewService Test] - Application layer")
class ReviewServiceTest extends MockTestBase {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ProductQueryRepository productQueryRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ImageService imageService;

    @Nested
    @DisplayName("[createReview]")
    class createReview {

        private CreateReviewRequest request;

        @BeforeEach
        void setUp() throws IOException {
            //given
            request = A.toCreateRequest();

            given(imageService.uploadImages(anyList(), anyString()))
                    .willReturn(generateImageUrls());
        }

        @Test
        @DisplayName("[success]")
        void success() {
            //given
            given(productQueryRepository.findById(1L))
                    .willReturn(generateProductOf());

            given(reviewRepository.save(any(Review.class)))
                    .willReturn(generateReviewHasId());

            //when
            Long result = reviewService.createReview(request, 1L);

            //then
            assertThat(result).isEqualTo(1L);
        }

        @Test
        @DisplayName("[Fail] 해당 Id의 상품이 존재하지 않아 실패")
        void failWhenProductNotFound() {
            //given
            given(productQueryRepository.findById(1L))
                    .willReturn(generateProductEmpty());

            //when, then
            assertThatThrownBy(() -> reviewService.createReview(request, 1L))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(GLOBAL_NOT_FOUND.getMessage());
        }
    }

    private Review generateReviewHasId() {
        Review review = A.toEntity(null, null, null);
        setField(review, "id", 1L);

        return review;
    }

    private Optional<Product> generateProductOf() {
        return Optional.of(VALID_PRODUCT.toEntity(null));
    }

    private Optional<Product> generateProductEmpty() {
        return Optional.empty();
    }

    private List<String> generateImageUrls() {
        return List.of("hello1.jpg", "hello2.jpg");
    }
}
