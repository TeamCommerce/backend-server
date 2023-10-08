package com.commerce.backendserver.auth.domain;

import com.commerce.backendserver.auth.domain.model.OAuthProvider;

import java.net.URI;

public interface OAuthUriGenerator {

    boolean isSupported(final OAuthProvider provider);

    URI generate();
}
