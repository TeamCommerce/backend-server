package com.commerce.backendserver.global.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PathMatcherInterceptor implements HandlerInterceptor {

	private final HandlerInterceptor target;
	private final PathMatcherContainer pathMatcherContainer;

	public PathMatcherInterceptor(final HandlerInterceptor target) {
		this.target = target;
		pathMatcherContainer = new PathMatcherContainer();
	}

	@Override
	public boolean preHandle(
		final HttpServletRequest request,
		final HttpServletResponse response,
		final Object handler
	) throws Exception {

		if (pathMatcherContainer.isInterceptorRequired(request.getServletPath(), request.getMethod())) {
			return target.preHandle(request, response, handler);
		}
		return true;
	}

	public PathMatcherInterceptor addIncludePathPattern(final String pathPattern, final HttpMethod method) {
		pathMatcherContainer.includePathPattern(pathPattern, method.toString());
		return this;
	}

	public PathMatcherInterceptor addExcludePathPattern(final String pathPattern, final HttpMethod method) {
		pathMatcherContainer.excludePathPattern(pathPattern, method.toString());
		return this;
	}
}
