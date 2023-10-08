package com.commerce.backendserver.auth.infra.oauth.google.model;

import com.commerce.backendserver.auth.domain.model.OAuthTokenInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleTokenInfo(
	String tokenType,
	String accessToken,
	String refreshToken,
	long expiresIn
) implements OAuthTokenInfo {

}
