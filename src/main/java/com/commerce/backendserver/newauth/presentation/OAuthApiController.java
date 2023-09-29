package com.commerce.backendserver.newauth.presentation;

import static org.springframework.http.HttpStatus.*;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backendserver.newauth.application.OAuthService;
import com.commerce.backendserver.newauth.application.dto.AuthTokenResponse;
import com.commerce.backendserver.newauth.presentation.dto.OAuthLoginRequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OAuthApiController {

	private final OAuthService oauthService;

	@GetMapping("/api/oauth/{provider}")
	public ResponseEntity<Void> redirectToOAuthAuthorization(
		@PathVariable final String provider,
		HttpServletResponse response
	) throws IOException {
		String authorizationUri = oauthService.getAuthorizationUri(provider);

		response.sendRedirect(authorizationUri);

		return new ResponseEntity<>(FOUND);
	}

	@GetMapping("/login/oauth2/code/{provider}")
	public ResponseEntity<AuthTokenResponse> login(
		@PathVariable final String provider,
		@ModelAttribute final OAuthLoginRequest request
	) {
		AuthTokenResponse response = oauthService.login(provider, request.code(), request.state());

		return ResponseEntity.ok(response);
	}
}
