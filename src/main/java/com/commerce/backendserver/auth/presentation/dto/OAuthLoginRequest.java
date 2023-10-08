package com.commerce.backendserver.auth.presentation.dto;

public record OAuthLoginRequest(
        String code,
        String state
) {
}
