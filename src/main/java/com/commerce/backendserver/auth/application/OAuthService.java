package com.commerce.backendserver.auth.application;

import com.commerce.backendserver.auth.application.dto.AuthTokenResponse;
import com.commerce.backendserver.auth.domain.AuthTokenManager;
import com.commerce.backendserver.auth.domain.OAuthConnector;
import com.commerce.backendserver.auth.domain.model.AuthToken;
import com.commerce.backendserver.auth.domain.model.OAuthMemberInfo;
import com.commerce.backendserver.auth.domain.model.OAuthTokenInfo;
import com.commerce.backendserver.member.domain.Member;
import com.commerce.backendserver.member.infra.persistence.MemberQueryRepository;
import com.commerce.backendserver.member.infra.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final OAuthFactory oauthFactory;
    private final AuthTokenManager tokenManager;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;

    public URI getAuthorizationUri(final String provider) {
        return oauthFactory
                .getOAuthUriGenerator(provider)
                .generate();
    }

    @Transactional
    public AuthTokenResponse login(
            final String provider,
            final String code,
            final String state
    ) {

        OAuthMemberInfo memberInfo = fetchMemberInfo(provider, code, state);

        Member member = saveOrUpdateMember(memberInfo);

        AuthToken authToken = tokenManager.generate(member.getId());
        return new AuthTokenResponse(authToken.accessToken(), authToken.refreshToken());
    }

    private OAuthMemberInfo fetchMemberInfo(
            final String provider,
            final String code,
            final String state
    ) {
        OAuthConnector connector = oauthFactory.getOAuthConnector(provider);
        OAuthTokenInfo oauthToken = connector.fetchToken(code, state);

        return connector.fetchMemberInfo(oauthToken.accessToken());
    }

    //todo : member 엔티티 oauthType 속성 제거 후 리팩토링
    private Member saveOrUpdateMember(final OAuthMemberInfo memberInfo) {
        Optional<Member> optionalMember = memberQueryRepository.findByOauthId(memberInfo.oauthId());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.updateFromOAuth(memberInfo.nickname());
            return member;
        }

        Member newMember = Member.createMember(
                memberInfo.nickname(),
                memberInfo.oauthId(),
                null
        );
        return memberRepository.save(newMember);
    }
}
