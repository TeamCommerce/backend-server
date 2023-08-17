package com.commerce.backendserver.auth.application.handler;

import com.commerce.backendserver.auth.domain.Token;
import com.commerce.backendserver.auth.domain.TokenQueryRepository;
import com.commerce.backendserver.auth.domain.TokenRepository;
import com.commerce.backendserver.auth.infra.jwt.JwtProvider;
import com.commerce.backendserver.auth.infra.oauth.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class CustomOAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private final JwtProvider tokenProvider;
    private final TokenRepository tokenRepository;
    private final TokenQueryRepository tokenQueryRepository;
    private final String successRedirectUrl;

    public CustomOAuthLoginSuccessHandler(
            JwtProvider tokenProvider,
            TokenRepository tokenRepository,
            TokenQueryRepository tokenQueryRepository,
            @Value("${oauth.success-redirect-url}") String successRedirectUrl
    ) {
        this.tokenProvider = tokenProvider;
        this.tokenRepository = tokenRepository;
        this.tokenQueryRepository = tokenQueryRepository;
        this.successRedirectUrl = successRedirectUrl;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        Long memberId = oauth2User.getId();

        String accessToken = tokenProvider.createAccessToken(memberId);
        String refreshToken = tokenProvider.createRefreshToken(memberId);

        saveOrUpdateToken(memberId, refreshToken);

        String redirectUrl = generateRedirectUrl(accessToken, refreshToken, oauth2User.getNickname());

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private void saveOrUpdateToken(Long memberId, String refreshToken) {
        tokenQueryRepository.findByMemberId(memberId)
                .ifPresentOrElse(token -> token.updateRefreshToken(refreshToken),
                        () -> tokenRepository.save(Token.of(refreshToken, memberId)));
    }

    private String generateRedirectUrl(String accessToken, String refreshToken, String name) {

        return UriComponentsBuilder.fromUriString(successRedirectUrl)
                .queryParam("access-token", accessToken)
                .queryParam("refresh-token", refreshToken)
                .queryParam("name", name)
                .build()
                .encode()
                .toUri()
                .toString();
    }
}
