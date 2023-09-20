package com.commerce.backendserver.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseWrapper<T> implements ListResponse<T> {

    private List<T> result;
}
