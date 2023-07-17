package com.board.global.exception;

import com.board.global.response.dto.CommonResponse;
import com.board.global.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.board.global.response.type.ErrorCode.*;

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

    @ExceptionHandler(BoardException.class)
    public CommonResponse<?> handleMemberException(BoardException e) {
        log.error("{} is occurred. {}", e.getErrorCode(), e.getErrorMessage());
        return responseService.failure(e.getErrorCode());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public CommonResponse<?> handlerUsernameNotFoundException(UsernameNotFoundException e) {
        log.error("UsernameNotFoundException is occurred.", e);
        return responseService.failure(LOAD_USER_FAILED);
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
