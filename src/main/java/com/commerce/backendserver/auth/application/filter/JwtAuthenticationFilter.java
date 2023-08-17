package com.commerce.backendserver.auth.application.filter;

import com.commerce.backendserver.auth.exception.AuthError;
import com.commerce.backendserver.auth.infra.jwt.JwtProvider;
import com.commerce.backendserver.auth.infra.oauth.UserPrincipal;
import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.member.domain.Member;
import com.commerce.backendserver.member.domain.MemberQueryRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberQueryRepository memberRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (hasBearer(bearer)) {
            chain.doFilter(request, response);
            return;
        }

        String token = extractToken(bearer);

        jwtProvider.validateToken(token);
        Long memberId = jwtProvider.getPayload(token);

        setAuthentication(findMember(memberId));
    }

    private void setAuthentication(Member member) {
        UserPrincipal oauthUser = UserPrincipal.of(member, null);

        OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(
                oauthUser,
                oauthUser.getAuthorities(),
                member.getOauthType()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> CommerceException.of(AuthError.TOKEN_INVALID));
    }

    private String extractToken(String bearer) {
        return bearer.substring("Bearer ".length());
    }

    private boolean hasBearer(String bearer) {
        return bearer == null || !bearer.startsWith("Bearer ");
    }
}
