package com.commerce.backendserver.newauth.application.dto;

public record AuthTokenResponse(
	String accessToken,
	String refreshToken
) {
}
