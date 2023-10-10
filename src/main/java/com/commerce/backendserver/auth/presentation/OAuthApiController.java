package com.commerce.backendserver.auth.presentation;

import static org.springframework.http.HttpStatus.*;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backendserver.auth.application.OAuthService;
import com.commerce.backendserver.auth.application.dto.AuthTokenResponse;
import com.commerce.backendserver.auth.presentation.dto.OAuthLoginRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OAuthApiController {

	private final OAuthService oauthService;

	@GetMapping("/api/oauth/{provider}")
	public ResponseEntity<Void> redirectToOAuthAuthorization(
		@PathVariable final String provider
	) {
		URI authorizationUri = oauthService.getAuthorizationUri(provider);

		HttpHeaders headers = createRedirectHeaders(authorizationUri);

		return new ResponseEntity<>(headers, FOUND);
	}

	@GetMapping("/login/oauth2/code/{provider}")
	public ResponseEntity<AuthTokenResponse> login(
		@PathVariable final String provider,
		@ModelAttribute final OAuthLoginRequest request
	) {
		URI redirectUri = oauthService.login(provider, request.code(), request.state());

		HttpHeaders headers = createRedirectHeaders(redirectUri);

		return new ResponseEntity<>(headers, FOUND);
	}

	private HttpHeaders createRedirectHeaders(URI redirectUri) {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(redirectUri);
		return headers;
	}
}
