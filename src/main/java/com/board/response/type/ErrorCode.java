package com.board.response.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생하였습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    CONSTRAINT_VIOLATION(HttpStatus.CONFLICT, "제약 조건 위반"),

    ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
    ALREADY_EXISTS_PHONE(HttpStatus.BAD_REQUEST, "이미 사용중인 전화번호입니다."),
    ALREADY_EXISTS_NICKNAME(HttpStatus.BAD_REQUEST, "이미 사용중인 닉네임입니다."),

    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
