package com.commerce.backendserver.auth.infra.oauth.kakao;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class KakaoProperties {

	private final String clientId;
	private final String clientSecret;
	private final String redirectUri;
	private final Set<String> scope;
	private final String authorizationEndpoint;
	private final String tokenEndpoint;
	private final String userinfoEndpoint;

	public KakaoProperties(
		@Value("${spring.oauth.kakao.client-id}") final String clientId,
		@Value("${spring.oauth.kakao.client-secret}") final String clientSecret,
		@Value("${spring.oauth.kakao.redirect-uri}") final String redirectUri,
		@Value("${spring.oauth.kakao.scope}") final Set<String> scope,
		@Value("${spring.oauth.kakao.authorization-endpoint}") final String authorizationEndpoint,
		@Value("${spring.oauth.kakao.token-endpoint}") final String tokenEndpoint,
		@Value("${spring.oauth.kakao.userinfo-endpoint}") final String userinfoEndpoint
	) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
		this.scope = scope;
		this.authorizationEndpoint = authorizationEndpoint;
		this.tokenEndpoint = tokenEndpoint;
		this.userinfoEndpoint = userinfoEndpoint;
	}
}
