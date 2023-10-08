package com.commerce.backendserver.auth.presentation;

import com.commerce.backendserver.auth.application.OAuthService;
import com.commerce.backendserver.auth.application.dto.AuthTokenResponse;
import com.commerce.backendserver.auth.presentation.dto.OAuthLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequiredArgsConstructor
public class OAuthApiController {

    private final OAuthService oauthService;

    @GetMapping("/api/oauth/{provider}")
    public ResponseEntity<Void> redirectToOAuthAuthorization(
            @PathVariable final String provider
    ) {
        URI authorizationUri = oauthService.getAuthorizationUri(provider);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(authorizationUri);

        return new ResponseEntity<>(headers, FOUND);
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
