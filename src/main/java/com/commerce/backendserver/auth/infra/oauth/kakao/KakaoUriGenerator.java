package com.commerce.backendserver.auth.infra.oauth.kakao;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.commerce.backendserver.auth.domain.OAuthUriGenerator;
import com.commerce.backendserver.auth.domain.model.OAuthProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KakaoUriGenerator implements OAuthUriGenerator {

	private final KakaoProperties properties;

	@Override
	public boolean isSupported(final OAuthProvider provider) {
		return provider.isKakao();
	}

	@Override
	public String generate() {
		return UriComponentsBuilder
			.fromUriString(properties.getAuthorizationEndpoint())
			.queryParam("response_type", "code")
			.queryParam("client_id", properties.getClientId())
			.queryParam("scope", String.join(" ", properties.getScope()))
			.queryParam("redirect_uri", properties.getRedirectUri())
			.queryParam("state", UUID.randomUUID().toString().replaceAll("-", ""))
			.build()
			.toUriString();
	}
}
