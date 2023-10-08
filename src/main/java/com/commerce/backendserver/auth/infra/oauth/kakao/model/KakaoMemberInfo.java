package com.commerce.backendserver.auth.infra.oauth.kakao.model;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

import com.commerce.backendserver.auth.domain.model.OAuthMemberInfo;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = SnakeCaseStrategy.class)
public record KakaoMemberInfo(
	String id,
	KakaoAccount kakaoAccount
) implements OAuthMemberInfo {

	@Override
	public String oauthId() {
		return id;
	}

	@Override
	public String nickname() {
		return kakaoAccount.profile.nickname;
	}

	@Override
	public String email() {
		return kakaoAccount.email;
	}

	@JsonNaming(value = SnakeCaseStrategy.class)
	public record KakaoAccount(
		Profile profile,
		String email
	) {
	}

	@JsonNaming(value = SnakeCaseStrategy.class)
	public record Profile(
		String nickname
	) {
	}
}
