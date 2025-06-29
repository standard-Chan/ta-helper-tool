package com.teachAssistantHelper.exception.domainException;

import com.teachAssistantHelper.exception.CustomException;
import com.teachAssistantHelper.exception.ErrorCode;

public class StudentException extends CustomException {
    public StudentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
