package com.commerce.backendserver.auth.infra.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
}
