package com.commerce.backendserver.auth.domain.model;

import java.util.Arrays;

import com.commerce.backendserver.auth.exception.AuthError;
import com.commerce.backendserver.global.exception.CommerceException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthProvider {

	GOOGLE("google"),
	KAKAO("kakao"),
	;

	private final String provider;

	public static OAuthProvider from(String provider) {
		return Arrays.stream(values())
			.filter(oauthType -> oauthType.getProvider().equals(provider))
			.findFirst()
			.orElseThrow(() -> CommerceException.of(AuthError.NOT_EXIST_OAUTH_TYPE));
	}

	public boolean isGoogle() {
		return this.equals(GOOGLE);
	}

	public boolean isKakao() {
		return this.equals(KAKAO);
	}
}
