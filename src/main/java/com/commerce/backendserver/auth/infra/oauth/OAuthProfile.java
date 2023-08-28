package com.commerce.backendserver.auth.infra.oauth;

import java.util.Map;

public interface OAuthProfile {

    boolean isSupport(OAuthType type);

    void initAttributes(Map<String, Object> attributes);

    String getId();

    OAuthType getProvider();

    String getName();
}
