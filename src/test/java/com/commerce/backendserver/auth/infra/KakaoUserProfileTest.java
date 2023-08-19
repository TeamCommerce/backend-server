package com.commerce.backendserver.auth.infra;

import com.commerce.backendserver.auth.infra.oauth.KakaoProfile;
import com.commerce.backendserver.auth.infra.oauth.OAuthType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[KakaoUserProfile Test] (Infra layer)")
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
    @DisplayName("[isSupport method")
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
    @DisplayName("[getId method]")
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
    @DisplayName("[getProvider method]")
    void getProviderTest() {
        //when
        OAuthType result = kakaoProfile.getProvider();

        //then
        assertThat(result).isEqualTo(OAuthType.KAKAO);
    }

    @Test
    @DisplayName("[getName method]")
    void getNameTest() {
        //given
        final String nickname = "nickname";
        HashMap<String, Object> kakaoAccount = new HashMap<>();

        kakaoAccount.put("nickname", nickname);
        attributes.put("kakao_account", kakaoAccount);

        kakaoProfile.initAttributes(attributes);

        //when
        String result = kakaoProfile.getName();

        //then
        assertThat(result).isEqualTo(nickname);
    }
}
