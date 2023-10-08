package com.commerce.backendserver.global.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseWrapper<T> implements ListResponse<T> {

	private List<T> result;
}
