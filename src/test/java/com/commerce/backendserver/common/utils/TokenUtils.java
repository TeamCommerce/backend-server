package com.commerce.backendserver.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TokenUtils {

    // This token has id 1L
    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjkyNDEyNjIzLCJleHAiOjIzMjMxMzI2MjN9.cPdGhsW8ySWPQYek0OEDd7PB37gx7TPR3hzy8gT6t_Y";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";
}
