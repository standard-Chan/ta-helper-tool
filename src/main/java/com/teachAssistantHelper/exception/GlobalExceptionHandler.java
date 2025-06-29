package com.teachAssistantHelper.exception;

import com.teachAssistantHelper.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorCode code = e.getErrorCode();
        return ResponseEntity
                .status(code.getStatus())
                .body(new ErrorResponse(code));
    }

    // 예상치 못한 예외 처리
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleException(Exception e) {
//        return ResponseEntity
//                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
//                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
//    }
}

