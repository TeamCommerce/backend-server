package com.commerce.backendserver.auth.infra.oauth;

import com.commerce.backendserver.global.exception.CommerceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.commerce.backendserver.auth.exception.AuthError.NOT_EXIST_OAUTH_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[OAuthProfileFactory Test] (Infra layer)")
class OAuthProfileFactoryTest {

    private static final String GOOGLE = "google";
    private static final String KAKAO = "kakao";
    private static final String NONE = "none";

    @Nested
    @DisplayName("[loadOAuthProfile method]")
    class LoadOAuthProfileTest {
        //given
        final OAuthProfileFactory factory = new OAuthProfileFactory(
                List.of(new GoogleProfile(), new KakaoProfile())
        );

        @Test
        @DisplayName("Google")
        void google() {
            //when
            OAuthProfile result = factory.loadOAuthProfile(GOOGLE, null);

            //then
            assertThat(result).isInstanceOf(GoogleProfile.class);
        }

        @Test
        @DisplayName("Kakao")
        void kakao() {
            //when
            OAuthProfile result = factory.loadOAuthProfile(KAKAO, null);

            //then
            assertThat(result).isInstanceOf(KakaoProfile.class);
        }

        @Test
        @DisplayName("Not supported type")
        void fail() {
            //when, then
            assertThatThrownBy(() -> factory.loadOAuthProfile(NONE, null))
                    .isInstanceOf(CommerceException.class)
                    .hasMessageContaining(NOT_EXIST_OAUTH_TYPE.getMessage());
        }
    }
}
