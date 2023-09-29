package com.commerce.backendserver.newauth.infra.oauth.kakao;

import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;

import com.commerce.backendserver.newauth.domain.OAuthUriProvider;
import com.commerce.backendserver.newauth.domain.model.OAuthProvider;
import com.commerce.backendserver.newauth.infra.oauth.kakao.model.KakaoProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KakaoUriProvider implements OAuthUriProvider {

	private final KakaoProperties properties;

	@Override
	public boolean isSupported(final OAuthProvider provider) {
		return provider.isKakao();
	}

	@Override
	public String generate(final String redirectUri) {
		return UriComponentsBuilder
			.fromUriString(properties.getAuthorizationEndpoint())
			.queryParam("response_type", "code")
			.queryParam("client_id", properties.getClientId())
			.queryParam("scope", String.join(" ", properties.getScope()))
			.queryParam("redirect_uri", redirectUri)
			.queryParam("state", UUID.randomUUID().toString().replaceAll("-", ""))
			.build()
			.toUriString();
	}
}