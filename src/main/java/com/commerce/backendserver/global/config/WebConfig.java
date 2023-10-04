package com.commerce.backendserver.global.config;

import static org.springframework.http.HttpMethod.*;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.commerce.backendserver.global.interceptor.AuthenticationInterceptor;
import com.commerce.backendserver.global.interceptor.PathMatcherInterceptor;
import com.commerce.backendserver.global.resolver.AuthInfoArgumentResolver;
import com.commerce.backendserver.auth.domain.AuthTokenManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AuthTokenManager authTokenManager;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:8080")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedHeaders("*");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		HandlerInterceptor authenticationInterceptor = new AuthenticationInterceptor(authTokenManager);

		PathMatcherInterceptor pathMatcherInterceptor = new PathMatcherInterceptor(authenticationInterceptor)
			.addExcludePathPattern("/api/products/**", GET)
			.addIncludePathPattern("/api/reviews", POST);

		registry
			.addInterceptor(pathMatcherInterceptor)
			.addPathPatterns("/api/**")
			.order(1);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new AuthInfoArgumentResolver(authTokenManager));
	}
}
