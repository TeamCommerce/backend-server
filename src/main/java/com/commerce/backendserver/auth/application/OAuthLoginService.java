package com.commerce.backendserver.auth.application;

import com.commerce.backendserver.auth.infra.oauth.OAuthProfile;
import com.commerce.backendserver.auth.infra.oauth.OAuthProfileFactory;
import com.commerce.backendserver.member.domain.Member;
import com.commerce.backendserver.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthLoginService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String ROLE_USER = "ROLE_USER";

    private final MemberRepository memberRepository;
    private final OAuthProfileFactory oauthProfileFactory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oauth2User.getAttributes();

        OAuthProfile oauthProfile = oauthProfileFactory.loadOAuthProfile(registrationId, attributes);

        saveOrUpdateMember(oauthProfile);

        return generateDefaultOAuth2User(userRequest, attributes);
    }

    private DefaultOAuth2User generateDefaultOAuth2User(OAuth2UserRequest userRequest, Map<String, Object> attributes) {
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        return new DefaultOAuth2User(
                AuthorityUtils.createAuthorityList(ROLE_USER),
                attributes,
                userNameAttributeName
        );
    }

    private void saveOrUpdateMember(OAuthProfile profile) {
        memberRepository.findByOauthId(profile.getId())
                .ifPresentOrElse(member -> member.updateFromOAuth(profile.getName()),
                        () -> {
                            Member member = Member.createMember(
                                    profile.getName(),
                                    profile.getId(),
                                    profile.getProvider().getDescription());
                            memberRepository.save(member);
                        });
    }
}
