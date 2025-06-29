package com.teachAssistantHelper.exception.domainException;

import com.teachAssistantHelper.exception.CustomException;
import com.teachAssistantHelper.exception.ErrorCode;

public class ClassEntityException extends CustomException {
  public ClassEntityException(ErrorCode code) {
    super(code);
  }
}

