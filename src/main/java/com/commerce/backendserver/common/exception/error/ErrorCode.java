package com.commerce.backendserver.common.exception.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

  String getMessage();

  HttpStatus getStatus();
}
