package com.commerce.backendserver.global.interceptor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class PathMatcherContainer {

	private final PathMatcher pathMatcher;
	private final Set<PathInfo> includePath;
	private final Set<PathInfo> excludePath;

	public PathMatcherContainer() {
		pathMatcher = new AntPathMatcher();
		includePath = new HashSet<>();
		excludePath = new HashSet<>();
	}

	public boolean isInterceptorRequired(final String path, final String method) {
		boolean isInclude = includePath.stream()
			.anyMatch(include -> matches(include, path, method));

		boolean isNotExclude = excludePath.stream()
			.noneMatch(exclude -> matches(exclude, path, method));

		return isNotExclude && isInclude;
	}

	public void includePathPattern(final String pathPattern, final String method) {
		includePath.add(new PathInfo(pathPattern, method));
	}

	public void excludePathPattern(final String pathPattern, final String method) {
		excludePath.add(new PathInfo(pathPattern, method));
	}

	private boolean matches(
		final PathInfo pathInfo,
		final String targetPath,
		final String targetMethod
	) {
		return pathMatcher.match(pathInfo.pathPattern, targetPath) && pathInfo.pathPattern.equals(targetMethod);
	}

	private record PathInfo(
		String pathPattern,
		String method
	) {
	}
}
