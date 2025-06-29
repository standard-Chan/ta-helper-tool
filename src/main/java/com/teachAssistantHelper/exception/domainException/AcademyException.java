package com.teachAssistantHelper.exception.domainException;

import com.teachAssistantHelper.exception.CustomException;
import com.teachAssistantHelper.exception.ErrorCode;

public class AcademyException extends CustomException {
    public AcademyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
