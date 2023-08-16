package com.commerce.backendserver.auth.application;

import com.commerce.backendserver.auth.infra.oauth.OAuthProfile;
import com.commerce.backendserver.auth.infra.oauth.OAuthProfileFactory;
import com.commerce.backendserver.auth.infra.oauth.UserPrincipal;
import com.commerce.backendserver.member.domain.Member;
import com.commerce.backendserver.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthLoginService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final OAuthProfileFactory oauthProfileFactory;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oauth2User.getAttributes();

        OAuthProfile oauthProfile = oauthProfileFactory.loadOAuthProfile(registrationId, attributes);

        Member member = saveOrUpdateMember(oauthProfile);

        return UserPrincipal.of(member, attributes);
    }

    private Member saveOrUpdateMember(OAuthProfile profile) {
        Member member = memberRepository.findByOauthId(profile.getId())
                .orElseGet(() -> {
                    Member newMember = Member.createMember(
                            profile.getName(),
                            profile.getId(),
                            profile.getProvider().getDescription());
                    return memberRepository.save(newMember);
                });

        member.updateFromOAuth(profile.getName());
        return member;
    }
}
