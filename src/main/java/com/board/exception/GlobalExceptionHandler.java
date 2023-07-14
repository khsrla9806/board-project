package com.board.exception;

import com.board.response.dto.CommonResponse;
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
    public CommonResponse<?> handleMemberException(MemberException e) {
        log.error("{} is occurred. {}", e.getErrorCode(), e.getErrorMessage());
        return responseService.failure(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException is occurred.", e);
        return responseService.failure(INVALID_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public CommonResponse<?> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        log.error("DataIntegrityViolationException is occurred.", e);
        return responseService.failure(CONSTRAINT_VIOLATION);
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleException(Exception e){
        log.error("Exception is occurred.", e);
        return responseService.failure(INTERNAL_SERVER_ERROR);
    }
}
