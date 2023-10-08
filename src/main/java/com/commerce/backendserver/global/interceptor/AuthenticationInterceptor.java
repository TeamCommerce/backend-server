package com.commerce.backendserver.global.interceptor;

import static com.commerce.backendserver.auth.exception.AuthError.*;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.commerce.backendserver.auth.domain.AuthTokenManager;
import com.commerce.backendserver.global.exception.CommerceException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER = "Bearer";

	private final AuthTokenManager authTokenManager;

	@Override
	public boolean preHandle(
		final HttpServletRequest request,
		final HttpServletResponse response,
		final Object handler
	) {
		String header = request.getHeader(AUTHORIZATION);

		validateHasAuthorizationHeader(header);
		validateAuthorizationHeader(header);

		return true;
	}

	private void validateHasAuthorizationHeader(final String header) {
		if (!StringUtils.hasText(header)) {
			throw CommerceException.of(NEED_LOGIN);
		}
	}

	private void validateAuthorizationHeader(final String header) {
		String[] splitHeader = header.split(" ");

		if (splitHeader.length != 2 || !splitHeader[0].equals(BEARER)) {
			throw CommerceException.of(INVALID_AUTHORIZATION_HEADER);
		}

		authTokenManager.validateToken(splitHeader[1]);
	}
}
