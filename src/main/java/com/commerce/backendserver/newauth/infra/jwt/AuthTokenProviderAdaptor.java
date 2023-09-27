package com.commerce.backendserver.newauth.infra.jwt;

import org.springframework.stereotype.Component;

import com.commerce.backendserver.newauth.domain.AuthTokenProvider;
import com.commerce.backendserver.newauth.domain.model.AuthToken;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthTokenProviderAdaptor implements AuthTokenProvider {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public AuthToken generate(final Long memberId) {
		String accessToken = jwtTokenProvider.createAccessToken(memberId);
		String refreshToken = jwtTokenProvider.createRefreshToken(memberId);
		return new AuthToken(accessToken, refreshToken);
	}

	@Override
	public Long getId(final String token) {
		return jwtTokenProvider.getPayload(token);
	}

	@Override
	public void validateToken(final String token) {
		jwtTokenProvider.validateToken(token);
	}
}
