package com.commerce.backendserver.newauth.infra.oauth.google.model;

import com.commerce.backendserver.newauth.domain.model.OAuthTokenResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleTokenResponse(
	String tokenType,
	String idToken,
	String accessToken,
	String refreshToken,
	long expiresIn
) implements OAuthTokenResponse {

}
