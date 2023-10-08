package com.commerce.backendserver.auth.domain;

import com.commerce.backendserver.auth.domain.model.AuthToken;

public interface AuthTokenManager {

    AuthToken generate(final Long memberId);

    Long getId(final String token);

    void validateToken(final String token);
}
