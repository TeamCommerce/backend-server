package com.commerce.backendserver.newauth.infra.oauth.kakao.model;

import com.commerce.backendserver.newauth.domain.model.OAuthMemberResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public record KakaoMemberResponse(
	String id,
	KakaoAccount kakaoAccount
) implements OAuthMemberResponse {

	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public record KakaoAccount(
		Profile profile,
		String email
	) {
	}

	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public record Profile(
		String nickname
	) {
	}

	@Override
	public String nickname() {
		return kakaoAccount.profile.nickname;
	}

	@Override
	public String email() {
		return kakaoAccount.email;
	}
}
