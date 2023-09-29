package com.commerce.backendserver.newauth.infra.oauth.kakao.model;

import com.commerce.backendserver.newauth.domain.model.OAuthMemberInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public record KakaoMemberInfo(
	String id,
	KakaoAccount kakaoAccount
) implements OAuthMemberInfo {

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
