package com.teachAssistantHelper.exception.domainException;

import com.teachAssistantHelper.exception.CustomException;
import com.teachAssistantHelper.exception.ErrorCode;

public class NoticeException extends CustomException {
    public NoticeException(ErrorCode errorCode) {
        super(errorCode);
    }
}

