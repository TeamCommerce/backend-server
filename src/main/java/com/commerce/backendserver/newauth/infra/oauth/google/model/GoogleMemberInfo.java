package com.commerce.backendserver.newauth.infra.oauth.google.model;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

import com.commerce.backendserver.newauth.domain.model.OAuthMemberInfo;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleMemberInfo(
	String sub,
	String name,
	String email
) implements OAuthMemberInfo {

	@Override
	public String oauthId() {
		return sub;
	}

	@Override
	public String nickname() {
		return name;
	}
}
