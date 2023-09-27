package com.commerce.backendserver.newauth.infra.oauth.google.model;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

import com.commerce.backendserver.newauth.domain.model.OAuthMemberResponse;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleMemberResponse(
	String sub,
	String name,
	String email
) implements OAuthMemberResponse {

	@Override
	public String id() {
		return sub;
	}

	@Override
	public String nickname() {
		return name;
	}
}
