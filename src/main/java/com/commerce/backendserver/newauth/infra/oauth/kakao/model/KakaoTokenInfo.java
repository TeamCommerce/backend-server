package com.commerce.backendserver.newauth.infra.oauth.kakao.model;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

import com.commerce.backendserver.newauth.domain.model.OAuthTokenInfo;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = SnakeCaseStrategy.class)
public record KakaoTokenInfo(
	String tokenType,
	String accessToken,
	String refreshToken,
	int expireIn
) implements OAuthTokenInfo {
}
