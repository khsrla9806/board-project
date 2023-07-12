package com.board.exception;

import com.board.response.dto.CommonResult;
import com.board.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.board.response.type.ErrorCode.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ResponseService responseService;

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<?> handleMemberException(MemberException e) {
        log.error("{} is occurred. {}", e.getErrorCode(), e.getErrorMessage());

        CommonResult result = responseService.getFailResult(e.getErrorCode());
        HttpStatus httpStatus = e.getHttpStatus();

        return ResponseEntity.status(httpStatus).body(result);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException is occurred.", e);

        CommonResult result = responseService.getFailResult(INVALID_REQUEST);
        HttpStatus httpStatus = INVALID_REQUEST.getHttpStatus();

        return ResponseEntity.status(httpStatus).body(result);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        log.error("DataIntegrityViolationException is occurred.", e);

        CommonResult result = responseService.getFailResult(CONSTRAINT_VIOLATION);
        HttpStatus httpStatus = CONSTRAINT_VIOLATION.getHttpStatus();

        return ResponseEntity.status(httpStatus).body(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        log.error("Exception is occurred.", e);

        CommonResult result = responseService.getFailResult(INTERNAL_SERVER_ERROR);
        HttpStatus httpStatus = INTERNAL_SERVER_ERROR.getHttpStatus();

        return ResponseEntity.status(httpStatus).body(result);
    }
}
