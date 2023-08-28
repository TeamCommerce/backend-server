package com.commerce.backendserver.auth.infra.oauth;

import com.commerce.backendserver.global.exception.CommerceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.commerce.backendserver.auth.exception.AuthError.NOT_EXIST_OAUTH_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[OAuthType Test] (Infra layer)")
class OAuthTypeTest {

    @Test
    @DisplayName("[isGoogle method]")
    void isGoogleTest() {
        //given
        OAuthType google = OAuthType.GOOGLE;

        //when
        boolean result = google.isGoogle();

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[isKakao method]")
    void isKakaoTest() {
        //given
        OAuthType kakao = OAuthType.KAKAO;

        //when
        boolean result = kakao.isKakao();

        //then
        assertThat(result).isTrue();
    }

    @Nested
    @DisplayName("[matchOAuthType method]")
    class matchOAuthTypeTest {
        @Test
        @DisplayName("google")
        void google() {
            //when
            OAuthType result = OAuthType.matchOAuthType("google");

            //then
            assertThat(result).isEqualTo(OAuthType.GOOGLE);
        }

        @Test
        @DisplayName("kakao")
        void kakao() {
            //when
            OAuthType result = OAuthType.matchOAuthType("kakao");

            //then
            assertThat(result).isEqualTo(OAuthType.KAKAO);
        }

        @Test
        @DisplayName("not supported")
        void fail() {
            //when, then
            assertThatThrownBy(() -> OAuthType.matchOAuthType("none"))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(NOT_EXIST_OAUTH_TYPE.getMessage());
        }
    }
}
