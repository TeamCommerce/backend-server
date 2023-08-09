package com.commerce.backendserver.global.exception;

import com.commerce.backendserver.global.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class CommerceException extends RuntimeException{

  private final ErrorCode errorCode;

  private CommerceException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public static CommerceException of(ErrorCode errorCode) {
    return new CommerceException(errorCode);
  }
}
