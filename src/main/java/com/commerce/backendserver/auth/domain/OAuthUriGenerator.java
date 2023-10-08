package com.commerce.backendserver.auth.domain;

import java.net.URI;

import com.commerce.backendserver.auth.domain.model.OAuthProvider;

public interface OAuthUriGenerator {

	boolean isSupported(final OAuthProvider provider);

	URI generate();
}
