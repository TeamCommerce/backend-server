package com.commerce.backendserver.auth.application.handler;

import com.commerce.backendserver.auth.infra.jwt.JwtProvider;
import com.commerce.backendserver.auth.infra.oauth.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class CustomOAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider tokenProvider;
    private final String successRedirectUrl;

    public CustomOAuthLoginSuccessHandler(
            JwtProvider tokenProvider,
            @Value("${oauth.success-redirect-url}") String successRedirectUrl
    ) {
        this.tokenProvider = tokenProvider;
        this.successRedirectUrl = successRedirectUrl;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();

        String redirectUrl = generateRedirectUrl(oauth2User);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private String generateRedirectUrl(CustomOAuth2User oauth2User) {
        Long memberId = oauth2User.getId();
        String accessToken = tokenProvider.createAccessToken(memberId);
        String refreshToken = tokenProvider.createRefreshToken(memberId);

        return UriComponentsBuilder.fromUriString(successRedirectUrl)
                .queryParam("access-token", accessToken)
                .queryParam("refresh-token", refreshToken)
                .queryParam("name", oauth2User.getName())
                .build()
                .encode()
                .toUri()
                .toString();
    }
}
