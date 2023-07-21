package com.module.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import com.module.response.type.ErrorCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    public ReportException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }
}
