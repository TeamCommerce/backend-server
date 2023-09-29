package com.commerce.backendserver.newauth.domain;

import com.commerce.backendserver.newauth.domain.model.OAuthProvider;

public interface OAuthUriGenerator {

	boolean isSupported(final OAuthProvider provider);

	String generate(final String redirectUri);
}
