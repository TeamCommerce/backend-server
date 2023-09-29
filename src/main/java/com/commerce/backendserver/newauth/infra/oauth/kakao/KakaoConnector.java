package com.commerce.backendserver.newauth.infra.oauth.kakao;

import static com.commerce.backendserver.auth.exception.AuthError.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.newauth.domain.OAuthConnector;
import com.commerce.backendserver.newauth.domain.model.OAuthMemberInfo;
import com.commerce.backendserver.newauth.domain.model.OAuthProvider;
import com.commerce.backendserver.newauth.domain.model.OAuthTokenInfo;
import com.commerce.backendserver.newauth.infra.oauth.kakao.model.KakaoMemberInfo;
import com.commerce.backendserver.newauth.infra.oauth.kakao.model.KakaoProperties;
import com.commerce.backendserver.newauth.infra.oauth.kakao.model.KakaoTokenInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KakaoConnector implements OAuthConnector {

	private static final String GRANT_TYPE = "authorization_code";
	private static final String BEARER = "Bearer ";

	private final RestTemplate restTemplate;
	private final KakaoProperties properties;

	@Override
	public boolean isSupported(final OAuthProvider provider) {
		return provider.isKakao();
	}

	@Override
	public OAuthTokenInfo fetchToken(String code, String state) {
		HttpHeaders headers = createHttpHeaders();

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", GRANT_TYPE);
		body.add("client_id", properties.getClientId());
		body.add("redirect_uri", properties.getRedirectUri());
		body.add("client_secret", properties.getClientSecret());
		body.add("state", state);
		body.add("code", code);

		HttpEntity<?> request = new HttpEntity<>(body, headers);

		try {
			return restTemplate.postForObject(
				properties.getTokenEndpoint(),
				request,
				KakaoTokenInfo.class
			);
		} catch (RestClientException e) {
			throw CommerceException.of(GOOGLE_OAUTH_ERROR);
		}
	}

	@Override
	public OAuthMemberInfo fetchMemberInfo(String accessToken) {
		HttpHeaders headers = createHttpHeaders();
		headers.set("Authorization", BEARER + accessToken);

		HttpEntity<?> request = new HttpEntity<>(headers);

		try {
			return restTemplate.exchange(
				properties.getUserinfoEndpoint(),
				GET,
				request,
				KakaoMemberInfo.class
			).getBody();
		} catch (RestClientException e) {
			throw CommerceException.of(GOOGLE_OAUTH_ERROR);
		}
	}

	private HttpHeaders createHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(APPLICATION_FORM_URLENCODED);
		return headers;
	}
}
