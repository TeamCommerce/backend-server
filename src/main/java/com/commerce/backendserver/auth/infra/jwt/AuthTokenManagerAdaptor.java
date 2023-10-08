package com.commerce.backendserver.auth.infra.jwt;

import com.commerce.backendserver.auth.domain.AuthTokenManager;
import com.commerce.backendserver.auth.domain.model.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenManagerAdaptor implements AuthTokenManager {

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
