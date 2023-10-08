package com.commerce.backendserver.auth.application;

import com.commerce.backendserver.auth.domain.OAuthConnector;
import com.commerce.backendserver.auth.domain.OAuthUriGenerator;
import com.commerce.backendserver.auth.domain.model.OAuthProvider;
import com.commerce.backendserver.global.exception.CommerceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.commerce.backendserver.auth.exception.AuthError.NOT_EXIST_OAUTH_TYPE;

@Component
@RequiredArgsConstructor
public class OAuthFactory {

    private final List<OAuthConnector> connectors;
    private final List<OAuthUriGenerator> uriProviders;

    public OAuthConnector getOAuthConnector(final String provider) {
        OAuthProvider authProvider = OAuthProvider.from(provider);

        return connectors.stream()
                .filter(connector -> connector.isSupported(authProvider))
                .findFirst()
                .orElseThrow(() -> CommerceException.of(NOT_EXIST_OAUTH_TYPE));
    }

    public OAuthUriGenerator getOAuthUriGenerator(final String provider) {
        OAuthProvider authProvider = OAuthProvider.from(provider);

        return uriProviders.stream()
                .filter(uriProvider -> uriProvider.isSupported(authProvider))
                .findFirst()
                .orElseThrow(() -> CommerceException.of(NOT_EXIST_OAUTH_TYPE));
    }
}
