package com.commerce.backendserver.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TokenUtils {

    // This token has id 1L
    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJfaWQiOjEsImlhdCI6MTY5MjMyNTQwOSwiZXhwIjoxNzE4NjA1NDA5fQ.m-G0pU2HDDhDxQOR4JAZE2FSvRVydTFbL1Ww2ByibZo";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";
}
