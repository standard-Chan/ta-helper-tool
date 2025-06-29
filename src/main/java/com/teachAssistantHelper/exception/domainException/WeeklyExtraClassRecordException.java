package com.teachAssistantHelper.exception.domainException;

import com.teachAssistantHelper.exception.CustomException;
import com.teachAssistantHelper.exception.ErrorCode;

public class WeeklyExtraClassRecordException extends CustomException {
    public WeeklyExtraClassRecordException(ErrorCode code) {
        super(code);
    }
}

