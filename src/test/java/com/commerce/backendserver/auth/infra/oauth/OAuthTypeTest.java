package com.commerce.backendserver.auth.infra.oauth;

import com.commerce.backendserver.global.exception.CommerceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.commerce.backendserver.auth.exception.AuthError.NOT_EXIST_OAUTH_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[OAuthType Test] - Infra layer")
class OAuthTypeTest {

    @Test
    @DisplayName("[isGoogle]")
    void isGoogleTest() {
        //given
        OAuthType google = OAuthType.GOOGLE;

        //when
        boolean result = google.isGoogle();

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[isKakao]")
    void isKakaoTest() {
        //given
        OAuthType kakao = OAuthType.KAKAO;

        //when
        boolean result = kakao.isKakao();

        //then
        assertThat(result).isTrue();
    }

    @Nested
    @DisplayName("[matchOAuthType]")
    class matchOAuthTypeTest {

        @Test
        @DisplayName("[Success] 'google' 타입 성공")
        void successWhenPresentGoogle() {
            //when
            OAuthType result = OAuthType.matchOAuthType("google");

            //then
            assertThat(result).isEqualTo(OAuthType.GOOGLE);
        }

        @Test
        @DisplayName("[Success] 'kakao' 타입 성공")
        void successWhenPresentKakao() {
            //when
            OAuthType result = OAuthType.matchOAuthType("kakao");

            //then
            assertThat(result).isEqualTo(OAuthType.KAKAO);
        }

        @Test
        @DisplayName("[Fail] 지원하지 않는 타입으로 인해 실패")
        void failWhenPresentNotSupportedType() {
            //when, then
            assertThatThrownBy(() -> OAuthType.matchOAuthType("none"))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(NOT_EXIST_OAUTH_TYPE.getMessage());
        }
    }
}
