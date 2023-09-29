package com.commerce.backendserver.newauth.infra.oauth.google.model;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class GoogleProperties {

	private final String clientId;
	private final String clientSecret;
	private final String redirectUri;
	private final Set<String> scope;

	public GoogleProperties(
		@Value("${spring.oauth.google.client-id}") final String clientId,
		@Value("${spring.oauth.google.client-secret}") final String clientSecret,
		@Value("${spring.oauth.google.redirect-uri}") final String redirectUri,
		@Value("${spring.oauth.google.scope}") final Set<String> scope
	) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
		this.scope = scope;
	}
}
