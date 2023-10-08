package com.commerce.backendserver.auth.domain;

import com.commerce.backendserver.auth.domain.model.OAuthMemberInfo;
import com.commerce.backendserver.auth.domain.model.OAuthProvider;
import com.commerce.backendserver.auth.domain.model.OAuthTokenInfo;

public interface OAuthConnector {

    boolean isSupported(final OAuthProvider provider);

    OAuthTokenInfo fetchToken(final String code, final String state);

    OAuthMemberInfo fetchMemberInfo(final String accessToken);
}
