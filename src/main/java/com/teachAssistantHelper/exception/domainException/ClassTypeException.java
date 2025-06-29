package com.teachAssistantHelper.exception.domainException;

import com.teachAssistantHelper.exception.CustomException;
import com.teachAssistantHelper.exception.ErrorCode;

public class ClassTypeException extends CustomException {
    public ClassTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
