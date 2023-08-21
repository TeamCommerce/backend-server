package com.commerce.backendserver.auth.infra.jwt;

import com.commerce.backendserver.global.exception.CommerceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.commerce.backendserver.auth.exception.AuthError.TOKEN_EXPIRED;
import static com.commerce.backendserver.auth.exception.AuthError.TOKEN_INVALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("[JwtProvider Test] (Infra layer)")
class JwtProviderTest {

    private static final String SECRET_KEY = "abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc";
    private static final long SUC_VALIDITY = 1111L;
    private static final long FAIL_VALIDITY = 0L;
    private static final Long MEMBER_ID = 1L;

    @Test
    @DisplayName("[createAccessToken, createRefreshToken method]")
    void createAccessTokenTest() {
        //given
        JwtProvider provider = new JwtProvider(SECRET_KEY, SUC_VALIDITY, SUC_VALIDITY);

        //when
        String accessToken = provider.createAccessToken(1L);
        String refreshToken = provider.createRefreshToken(1L);

        //then
        assertToken(provider, accessToken);
        assertToken(provider, refreshToken);
    }

    @Test
    @DisplayName("[getPayload method]")
    void getPayloadTest() {
        //given
        JwtProvider provider = new JwtProvider(SECRET_KEY, SUC_VALIDITY, SUC_VALIDITY);
        String token = provider.createAccessToken(MEMBER_ID);

        //when
        Long result = provider.getPayload(token);

        //then
        assertThat(result).isEqualTo(MEMBER_ID);
    }

    @Nested
    @DisplayName("[validateToken method]")
    class validateTokenTest {

        @Test
        @DisplayName("success")
        void success() {
            //given
            JwtProvider provider = new JwtProvider(SECRET_KEY, SUC_VALIDITY, SUC_VALIDITY);
            String token = provider.createAccessToken(MEMBER_ID);

            //when, then
            assertDoesNotThrow(() -> provider.validateToken(token));
        }

        @Test
        @DisplayName("fail by expired token")
        void failByExpired() {
            //given
            JwtProvider provider = new JwtProvider(SECRET_KEY, FAIL_VALIDITY, FAIL_VALIDITY);
            String token = provider.createAccessToken(MEMBER_ID);

            //when, then
            assertThatThrownBy(() -> provider.validateToken(token))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(TOKEN_EXPIRED.getMessage());
        }

        @Test
        @DisplayName("fail by invalid token")
        void failByInvalid() {
            //given
            final String invalidKey = "kkwqeqeewwekkkkklkkkkkkdskddfdfdfdfdkfkkkdfddfd";
            JwtProvider invalidProvider = new JwtProvider(invalidKey, SUC_VALIDITY, SUC_VALIDITY);
            String token = invalidProvider.createAccessToken(MEMBER_ID);

            JwtProvider provider = new JwtProvider(SECRET_KEY, SUC_VALIDITY, SUC_VALIDITY);

            //when, then
            assertThatThrownBy(() -> provider.validateToken(token))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(TOKEN_INVALID.getMessage());
        }
    }

    private void assertToken(JwtProvider provider, String result) {
        assertAll(
                () -> assertDoesNotThrow(() -> provider.validateToken(result)),
                () -> assertThat(provider.getPayload(result)).isEqualTo(1L)
        );
    }
}
