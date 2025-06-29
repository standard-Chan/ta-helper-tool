package com.teachAssistantHelper.exception.domainException;

import com.teachAssistantHelper.exception.CustomException;
import com.teachAssistantHelper.exception.ErrorCode;

public class WeeklyClassRecordException extends CustomException {
    public WeeklyClassRecordException(ErrorCode code) {
        super(code);
    }
}

