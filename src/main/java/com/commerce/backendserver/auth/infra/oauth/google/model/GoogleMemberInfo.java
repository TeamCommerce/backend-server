package com.commerce.backendserver.auth.infra.oauth.google.model;

import com.commerce.backendserver.auth.domain.model.OAuthMemberInfo;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleMemberInfo(
        String sub,
        String name,
        String email
) implements OAuthMemberInfo {

    @Override
    public String oauthId() {
        return sub;
    }

    @Override
    public String nickname() {
        return name;
    }
}
