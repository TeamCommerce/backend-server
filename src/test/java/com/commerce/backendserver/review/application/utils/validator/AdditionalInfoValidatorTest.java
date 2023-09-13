package com.commerce.backendserver.review.application.utils.validator;

import com.commerce.backendserver.global.exception.CommerceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.commerce.backendserver.common.fixture.ReviewFixture.A;
import static com.commerce.backendserver.review.exception.ReviewError.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[AdditionalInfoValidator Test] (Application layer)")
class AdditionalInfoValidatorTest {

    private final AdditionalInfoValidator validator = new AdditionalInfoValidator();

    @Nested
    @DisplayName("[isValid method]")
    class isValid {

        @Test
        @DisplayName("success")
        void success() {
            //given
            Set<String> stringInfoSet = A.getStringInfoSet();

            //when
            boolean result = validator.isValid(stringInfoSet, null);

            //then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("success by value is null")
        void successByValueIsNull() {
            //when
            boolean result = validator.isValid(null, null);

            //then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("fail by invalid format")
        void failByInvalidFormat() {
            //given
            Set<String> invalidValue = Set.of("SIZE|Large");

            //when, then
            assertThatThrownBy(() -> validator.isValid(invalidValue, null))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(INVALID_ADDITIONAL_INFO.getMessage());
        }

        @Test
        @DisplayName("fail by invalid integer value")
        void failByInvalidIntegerValue() {
            //given
            Set<String> invalidValue = Set.of("HEIGHT/150cm");

            //when, then
            assertThatThrownBy(() -> validator.isValid(invalidValue, null))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(INVALID_INTEGER_INFO_VALUE.getMessage());
        }

        @Test
        @DisplayName("fail by invalid not exist info name")
        void failByNotExistInfoName() {
            //given
            Set<String> invalidValue = Set.of("HELLO/Large");

            //when, then
            assertThatThrownBy(() -> validator.isValid(invalidValue, null))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(NOT_EXIST_INFO_NAME.getMessage());
        }
    }
}
