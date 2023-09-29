package com.commerce.backendserver.newauth.infra.oauth.google;

import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;

import com.commerce.backendserver.newauth.domain.OAuthUriProvider;
import com.commerce.backendserver.newauth.domain.model.OAuthProvider;
import com.commerce.backendserver.newauth.infra.oauth.google.model.GoogleProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoogleUriProvider implements OAuthUriProvider {

	private final GoogleProperties properties;

	@Override
	public boolean isSupported(final OAuthProvider provider) {
		return provider.isGoogle();
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
