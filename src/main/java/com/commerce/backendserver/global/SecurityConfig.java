package com.commerce.backendserver.global;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(configurer -> configurer.configurationSource(request -> {
              CorsConfiguration cors = new CorsConfiguration();
              cors.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://106.10.59.143:8080"));
              cors.setAllowedMethods(Collections.singletonList("*"));
              cors.setAllowedHeaders(Collections.singletonList("*"));
              cors.setAllowCredentials(true);
              return cors;
            }
        ))
        .formLogin(AbstractHttpConfigurer::disable)
        .sessionManagement(configurer ->
            configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }
}
