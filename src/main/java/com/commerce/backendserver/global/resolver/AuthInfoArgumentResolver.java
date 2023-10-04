package com.commerce.backendserver.global.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.commerce.backendserver.global.annotation.FetchAuthInfo;
import com.commerce.backendserver.global.resolver.dto.AuthInfo;
import com.commerce.backendserver.auth.domain.AuthTokenManager;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthInfoArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String AUTHORIZATION = "Authorization";

	private final AuthTokenManager authTokenManager;

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		return parameter.hasParameterAnnotation(FetchAuthInfo.class);
	}

	@Override
	public Object resolveArgument(
		final MethodParameter parameter,
		final ModelAndViewContainer mavContainer,
		final NativeWebRequest webRequest,
		final WebDataBinderFactory binderFactory
	) {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		//정확히 어떤 상황에서 request 가 null 이 되는지 알 수 없어서 검증을 통해 실패 시 서버 예외로 넘기기 위함
		assert request != null;

		String header = request.getHeader(AUTHORIZATION);
		String token = header.split(" ")[1];

		return new AuthInfo(authTokenManager.getId(token));
	}
}
