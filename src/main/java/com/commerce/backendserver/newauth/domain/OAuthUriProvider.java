package com.commerce.backendserver.newauth.domain;

import com.commerce.backendserver.newauth.domain.model.OAuthProvider;

public interface OAuthUriProvider {

	boolean isSupported(final OAuthProvider provider);

	String generate(final String redirectUri);
}
