package com.commerce.backendserver.global.interceptor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.http.HttpServletRequest;

public class PathMatcher {

	private final Set<RequestMatcher> includePath;
	private final Set<RequestMatcher> excludePath;

	public PathMatcher() {
		includePath = new HashSet<>();
		excludePath = new HashSet<>();
	}

	public boolean isInterceptorRequired(final HttpServletRequest request) {
		boolean isInclude = includePath.stream()
			.anyMatch(include -> include.matches(request));

		boolean isNotExclude = excludePath.stream()
			.noneMatch(exclude -> exclude.matches(request));

		return isNotExclude && isInclude;
	}

	public void includePathPattern(final String pathPattern, final HttpMethod method) {
		includePath.add(new AntPathRequestMatcher(pathPattern, method.toString()));
	}

	public void excludePathPattern(final String pathPattern, final HttpMethod method) {
		excludePath.add(new AntPathRequestMatcher(pathPattern, method.toString()));
	}
}
