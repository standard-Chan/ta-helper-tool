package com.teachAssistantHelper.exception.domainException;

import com.teachAssistantHelper.exception.CustomException;
import com.teachAssistantHelper.exception.ErrorCode;

public class StaffException extends CustomException {
    public StaffException(ErrorCode errorCode) {
        super(errorCode);
    }
}

