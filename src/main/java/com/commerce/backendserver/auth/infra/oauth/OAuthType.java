package com.commerce.backendserver.auth.infra.oauth;

import com.commerce.backendserver.auth.exception.AuthError;
import com.commerce.backendserver.global.exception.CommerceException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OAuthType {
    GOOGLE("google"),
    KAKAO("kakao"),
    ;

    private final String description;

    public boolean isGoogle() {
        return this.equals(GOOGLE);
    }

    public boolean isKakao() {
        return this.equals(KAKAO);
    }

    public static OAuthType matchOAuthType(String registrationId) {
        return Arrays.stream(values())
                .filter(oauthType -> oauthType.getDescription().equals(registrationId))
                .findFirst()
                .orElseThrow(() -> CommerceException.of(AuthError.NOT_EXIST_OAUTH_TYPE));
    }
}
