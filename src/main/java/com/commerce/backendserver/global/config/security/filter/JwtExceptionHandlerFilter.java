package com.commerce.backendserver.global.config.security.filter;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.global.exception.error.ErrorCode;
import com.commerce.backendserver.global.exception.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        try {
            chain.doFilter(request, response);
        } catch (CommerceException e) {
            ErrorCode errorCode = e.getErrorCode();

            response.setStatus(errorCode.getStatus().value());
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            ErrorResponse errorResponse = ErrorResponse.of(errorCode);

            objectMapper.writeValue(response.getOutputStream(), errorResponse);
        }
    }
}
