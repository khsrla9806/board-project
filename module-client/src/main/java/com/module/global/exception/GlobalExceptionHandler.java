package com.module.global.exception;

import com.module.response.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import com.module.response.service.ResponseService;

import static com.module.response.type.ErrorCode.*;


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

    @ExceptionHandler(ReportException.class)
    public CommonResponse<?> handleReportException(ReportException e) {
        log.error("{} is occurred. {}", e.getErrorCode(), e.getErrorMessage());
        return responseService.failure(e.getErrorCode());
    }

    @ExceptionHandler(BoardException.class)
    public CommonResponse<?> handleBoardException(BoardException e) {
        log.error("{} is occurred. {}", e.getErrorCode(), e.getErrorMessage());
        return responseService.failure(e.getErrorCode());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public CommonResponse<?> handleUploadFileException(MaxUploadSizeExceededException e) {
        log.error("MaxUploadSizeExceededException is occurred.", e);
        return responseService.failure(FILE_MAXIMUM_SIZE);
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
