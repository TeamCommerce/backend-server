package com.commerce.backendserver.auth.application.handler;

import com.commerce.backendserver.auth.domain.TokenRepository;
import com.commerce.backendserver.auth.infra.oauth.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        tokenRepository.deleteByMemberId(oauthUser.getId());
    }
}
