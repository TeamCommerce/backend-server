package com.commerce.backendserver.auth.infra.oauth;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KakaoProfile implements OAuthProfile {

    private Map<String, Object> attributes;

    @Override
    public boolean isSupport(OAuthType type) {
        return type.isKakao();
    }

    @Override
    public void initAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public OAuthType getProvider() {
        return OAuthType.KAKAO;
    }

    @Override
    public String getName() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return (String) kakaoAccount.get("nickname");
    }
}
