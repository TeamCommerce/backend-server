package com.commerce.backendserver.newauth.application;

import static com.commerce.backendserver.auth.exception.AuthError.*;

import java.util.List;

import org.springframework.stereotype.Component;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.newauth.domain.OAuthConnector;
import com.commerce.backendserver.newauth.domain.OAuthUriProvider;
import com.commerce.backendserver.newauth.domain.model.OAuthProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthFactory {

	private final List<OAuthConnector> connectors;
	private final List<OAuthUriProvider> uriProviders;

	public OAuthConnector getOAuthConnector(final String provider) {
		OAuthProvider authProvider = OAuthProvider.from(provider);

		return connectors.stream()
			.filter(connector -> connector.isSupported(authProvider))
			.findFirst()
			.orElseThrow(() -> CommerceException.of(NOT_EXIST_OAUTH_TYPE));
	}

	public OAuthUriProvider getOAuthUriGenerator(final String provider) {
		OAuthProvider authProvider = OAuthProvider.from(provider);

		return uriProviders.stream()
			.filter(uriProvider -> uriProvider.isSupported(authProvider))
			.findFirst()
			.orElseThrow(() -> CommerceException.of(NOT_EXIST_OAUTH_TYPE));
	}
}
