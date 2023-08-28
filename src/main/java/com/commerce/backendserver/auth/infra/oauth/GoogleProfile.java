package com.commerce.backendserver.auth.infra.oauth;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GoogleProfile implements OAuthProfile{

    private Map<String, Object> attributes;

    @Override
    public boolean isSupport(OAuthType type) {
        return type.isGoogle();
    }

    @Override
    public void initAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public OAuthType getProvider() {
        return OAuthType.GOOGLE;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
