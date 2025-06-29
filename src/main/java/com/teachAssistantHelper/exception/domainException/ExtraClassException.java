package com.teachAssistantHelper.exception.domainException;

import com.teachAssistantHelper.exception.CustomException;
import com.teachAssistantHelper.exception.ErrorCode;

public class ExtraClassException extends CustomException {
  public ExtraClassException(ErrorCode code) {
    super(code);
  }
}
