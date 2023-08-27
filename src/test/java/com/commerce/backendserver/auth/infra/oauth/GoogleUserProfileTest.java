package com.commerce.backendserver.auth.infra.oauth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[GoogleUserProfile Test] (Infra layer)")
class GoogleUserProfileTest {

    private GoogleProfile googleProfile;
    private Map<String, Object> attributes;

    @BeforeEach
    void setUp() {
        //given
        googleProfile = new GoogleProfile();
        attributes = new HashMap<>();
    }

    @Test
    @DisplayName("[isSupport method")
    void isSupportTest() {
        //when
        boolean success = googleProfile.isSupport(OAuthType.GOOGLE);
        boolean fail = googleProfile.isSupport(OAuthType.KAKAO);

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
        final String id = "googleId";
        attributes.put("sub", id);
        googleProfile.initAttributes(attributes);

        //when
        String result = googleProfile.getId();

        //then
        assertThat(result).isEqualTo(id);
    }

    @Test
    @DisplayName("[getProvider method]")
    void getProviderTest() {
        //when
        OAuthType result = googleProfile.getProvider();

        //then
        assertThat(result).isEqualTo(OAuthType.GOOGLE);
    }

    @Test
    @DisplayName("[getName method]")
    void getNameTest() {
        //given
        final String name = "name";
        attributes.put("name", name);
        googleProfile.initAttributes(attributes);

        //when
        String result = googleProfile.getName();

        //then
        assertThat(result).isEqualTo(name);
    }
}
