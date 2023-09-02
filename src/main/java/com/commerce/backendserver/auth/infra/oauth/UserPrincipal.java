package com.commerce.backendserver.auth.infra.oauth;

import com.commerce.backendserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPrincipal implements CustomOAuth2User {
    private static final String ROLE_USER = "ROLE_USER";

    private final Member member;
    private final Map<String, Object> attributes;

    public static UserPrincipal of(Member member, Map<String, Object> attributes) {
        return new UserPrincipal(member, attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(ROLE_USER));
    }

    @Override
    public String getName() {
        return member.getOauthId();
    }

    @Override
    public Long getId() {
        return member.getId();
    }

    @Override
    public String getNickname() {
        return member.getNickname();
    }
}
