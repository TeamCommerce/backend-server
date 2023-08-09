package com.commerce.backendserver.common.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalError implements ErrorCode{
  ;

  private final String message;

  private final HttpStatus status;
}
