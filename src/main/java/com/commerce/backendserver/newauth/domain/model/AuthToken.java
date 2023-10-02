package com.commerce.backendserver.newauth.domain.model;

public record AuthToken(
	String accessToken,
	String refreshToken
) {
}
