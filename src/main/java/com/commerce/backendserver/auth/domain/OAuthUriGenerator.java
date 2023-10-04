package com.commerce.backendserver.auth.domain;

import com.commerce.backendserver.auth.domain.model.OAuthProvider;

public interface OAuthUriGenerator {

	boolean isSupported(final OAuthProvider provider);

	String generate();
}
