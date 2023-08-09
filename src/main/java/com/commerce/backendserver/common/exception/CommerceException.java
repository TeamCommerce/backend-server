package com.commerce.backendserver.common.exception;

import com.commerce.backendserver.common.exception.error.ErrorCode;
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
