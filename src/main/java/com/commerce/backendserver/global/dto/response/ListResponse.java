package com.commerce.backendserver.global.dto.response;

import java.util.List;

@FunctionalInterface
public interface ListResponse<T> {

	List<T> getResult();
}
