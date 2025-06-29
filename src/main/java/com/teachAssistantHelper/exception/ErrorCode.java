package com.teachAssistantHelper.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_INPUT("입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // Academy
    ACADEMY_NOT_FOUND("해당 학원이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    // ClassType
    CLASS_TYPE_NOT_FOUND("해당 클래스 타입이 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    // STAFF
    UNAUTHORIZED_ROLE("해당 작업을 수행할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    STAFF_NOT_FOUND("해당 Staff가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_USER_ID("이미 존재하는 사용자 ID입니다.", HttpStatus.CONFLICT),

    // Class
    CLASS_NOT_FOUND("해당 수업이 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    // Notice
    NOTICE_NOT_FOUND("해당 공지가 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    // Student
    STUDENT_NOT_FOUND("해당 학생이 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    // Extra-Class
    EXTRA_CLASS_NOT_FOUND("해당 보충수업이 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    // 주간 수업 기록
    WEEKLY_RECORD_NOT_FOUND("해당 주간 수업 기록이 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    // 주간 보충 수업 기록
    WEEKLY_EXTRA_RECORD_NOT_FOUND("해당 보충수업 주간 기록이 존재하지 않습니다.", HttpStatus.NOT_FOUND)
    ;







    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}