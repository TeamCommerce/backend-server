package com.commerce.backendserver.auth.application.dto;

public record AuthTokenResponse(
        String accessToken,
        String refreshToken
) {
}
