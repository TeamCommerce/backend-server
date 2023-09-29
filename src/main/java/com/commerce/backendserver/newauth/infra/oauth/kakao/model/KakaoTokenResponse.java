package com.commerce.backendserver.newauth.infra.oauth.kakao.model;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

import com.commerce.backendserver.newauth.domain.model.OAuthTokenResponse;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = SnakeCaseStrategy.class)
public record KakaoTokenResponse(
	String tokenType,
	String accessToken,
	String refreshToken,
	int expireIn
) implements OAuthTokenResponse {
}
