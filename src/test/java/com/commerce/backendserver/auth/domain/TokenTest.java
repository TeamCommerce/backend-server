package com.commerce.backendserver.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Token Test] - Domain layer")
class TokenTest {

    @Test
    @DisplayName("[Construct method]")
    void issueTest() {
        //given
        String refreshToken = "refreshToken";
        Long memberId = 1L;

        //when
        Token result = Token.issue(refreshToken, memberId);

        //then
        assertAll(
                () -> assertThat(result.getRefreshToken()).isEqualTo(refreshToken),
                () -> assertThat(result.getMemberId()).isEqualTo(memberId)
        );
    }
}
