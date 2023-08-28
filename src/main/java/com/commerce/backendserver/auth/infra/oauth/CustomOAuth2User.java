package com.commerce.backendserver.auth.infra.oauth;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CustomOAuth2User extends OAuth2User {

    Long getId();

    String getNickname();
}
