package com.teachAssistantHelper.dto;

import com.teachAssistantHelper.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String message;
    private final int status;

    public ErrorResponse(ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus().value();
    }
}

