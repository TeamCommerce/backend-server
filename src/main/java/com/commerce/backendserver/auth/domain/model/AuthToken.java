package com.commerce.backendserver.auth.domain.model;

public record AuthToken(
        String accessToken,
        String refreshToken
) {
}
