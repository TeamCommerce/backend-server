package com.commerce.backendserver.auth.infra.oauth.kakao.model;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

import com.commerce.backendserver.auth.domain.model.OAuthTokenInfo;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = SnakeCaseStrategy.class)
public record KakaoTokenInfo(
	String tokenType,
	String accessToken,
	String refreshToken,
	int expireIn
) implements OAuthTokenInfo {
}
