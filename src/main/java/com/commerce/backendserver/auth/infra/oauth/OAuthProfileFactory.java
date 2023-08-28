package com.commerce.backendserver.auth.infra.oauth;

import com.commerce.backendserver.auth.exception.AuthError;
import com.commerce.backendserver.global.exception.CommerceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthProfileFactory {

    private final List<OAuthProfile> profiles;

    public OAuthProfile loadOAuthProfile(String registrationId, Map<String, Object> attributes) {
        OAuthType oauthType = OAuthType.matchOAuthType(registrationId);

        OAuthProfile oauthProfile = profiles.stream()
                .filter(profile -> profile.isSupport(oauthType))
                .findFirst()
                .orElseThrow(() -> CommerceException.of(AuthError.NOT_EXIST_OAUTH_TYPE));

        oauthProfile.initAttributes(attributes);

        return oauthProfile;
    }
}
