package com.commerce.backendserver.common.authorize;

import com.commerce.backendserver.auth.infra.oauth.UserPrincipal;
import com.commerce.backendserver.member.domain.Member;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.test.util.ReflectionTestUtils;

import static com.commerce.backendserver.common.fixture.MemberFixture.A;

public class CustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    private static Member generateMember() {
        Member member = A.toEntity();
        ReflectionTestUtils.setField(member, "id", 1L);

        return member;
    }

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {

        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        final Member member = generateMember();

        UserPrincipal oauthUser = UserPrincipal.of(member, null);

        OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(
                oauthUser,
                oauthUser.getAuthorities(),
                member.getOauthType()
        );

        securityContext.setAuthentication(authentication);

        return securityContext;
    }
}
