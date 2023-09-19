package com.commerce.backendserver.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleResponseWrapper<T> {

    private T result;
}
