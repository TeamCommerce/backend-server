package com.commerce.backendserver.global.config;

import com.commerce.backendserver.auth.application.filter.JwtAuthenticationFilter;
import com.commerce.backendserver.auth.application.filter.JwtExceptionHandlerFilter;
import com.commerce.backendserver.auth.infra.jwt.JwtProvider;
import com.commerce.backendserver.member.domain.MemberQueryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthLoginService;
    private final SimpleUrlAuthenticationSuccessHandler loginSuccessHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final String serverDomain;

    public SecurityConfig(
            ObjectMapper objectMapper,
            JwtProvider jwtProvider,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthLoginService,
            SimpleUrlAuthenticationSuccessHandler loginSuccessHandler,
            AuthenticationEntryPoint authenticationEntryPoint,
            MemberQueryRepository memberQueryRepository,
            @Value("${server.domain}") String serverDomain
    ) {
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProvider, memberQueryRepository);
        this.jwtExceptionHandlerFilter = new JwtExceptionHandlerFilter(objectMapper);
        this.oauthLoginService = oauthLoginService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.serverDomain = serverDomain;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(configurer -> configurer.configurationSource(request -> {
                            CorsConfiguration cors = new CorsConfiguration();
                            cors.setAllowedOrigins(Arrays.asList("http://localhost:8080", serverDomain));
                            cors.setAllowedMethods(Collections.singletonList("*"));
                            cors.setAllowedHeaders(Collections.singletonList("*"));
                            cors.setAllowCredentials(true);
                            return cors;
                        }
                ))
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry -> registry
                        //모든 요청에 대해 인증 요구
                        .requestMatchers(new AntPathRequestMatcher("/api/products/**", GET.toString())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                //Jwt 필터
                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionHandlerFilter, SecurityContextHolderFilter.class)
                //oauth
                .oauth2Login(configurer -> configurer
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(oauthLoginService))
                        .successHandler(loginSuccessHandler))
                // 인증 예외 핸들러
                .exceptionHandling(configurer -> configurer
                        .authenticationEntryPoint(authenticationEntryPoint));

        return http.build();
    }
}
