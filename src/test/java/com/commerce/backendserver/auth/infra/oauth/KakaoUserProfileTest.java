package com.commerce.backendserver.auth.infra.oauth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[KakaoUserProfile Test] - Infra layer")
class KakaoUserProfileTest {

    private KakaoProfile kakaoProfile;
    private Map<String, Object> attributes;

    @BeforeEach
    void setUp() {
        //given
        kakaoProfile = new KakaoProfile();
        attributes = new HashMap<>();
    }

    @Test
    @DisplayName("[isSupport]")
    void isSupportTest() {
        //when
        boolean success = kakaoProfile.isSupport(OAuthType.KAKAO);
        boolean fail = kakaoProfile.isSupport(OAuthType.GOOGLE);

        //then
        assertAll(
                () -> assertThat(success).isTrue(),
                () -> assertThat(fail).isFalse()
        );
    }

    @Test
    @DisplayName("[getId]")
    void getIdTest() {
        //given
        final String id = "kakaoId";
        attributes.put("id", id);
        kakaoProfile.initAttributes(attributes);

        //when
        String result = kakaoProfile.getId();

        //then
        assertThat(result).isEqualTo(id);
    }

    @Test
    @DisplayName("[getProvider]")
    void getProviderTest() {
        //when
        OAuthType result = kakaoProfile.getProvider();

        //then
        assertThat(result).isEqualTo(OAuthType.KAKAO);
    }

    @Test
    @DisplayName("[getName]")
    void getNameTest() {
        //given
        final String nickname = "nickname";

        Map<String, Object> kakaoAccount = new HashMap<>();
        Map<String, Object> profile = new HashMap<>();

        profile.put("nickname", nickname);
        kakaoAccount.put("profile", profile);
        attributes.put("kakao_account", kakaoAccount);

        kakaoProfile.initAttributes(attributes);

        //when
        String result = kakaoProfile.getName();

        //then
        assertThat(result).isEqualTo(nickname);
    }
}
