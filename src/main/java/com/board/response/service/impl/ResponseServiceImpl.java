package com.board.response.service.impl;

import com.board.response.dto.CommonResponse;
import com.board.response.service.ResponseService;
import com.board.response.type.ErrorCode;
import com.board.response.type.SuccessCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Override
    public <T> CommonResponse<T> success(T data, SuccessCode successCode) {
        return CommonResponse.<T>builder()
                .success(true)
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .data(data)
                .build();
    }

    @Override
    public <T> CommonResponse<List<T>> successList(List<T> data, SuccessCode successCode) {
        return CommonResponse.<List<T>>builder()
                .success(true)
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .data(data)
                .build();
    }

    @Override
    public CommonResponse<?> successWithNoContent(SuccessCode successCode) {
        return CommonResponse.builder()
                .success(true)
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .build();
    }

    @Override
    public <T> CommonResponse<T> failure(ErrorCode errorCode) {
        return CommonResponse.<T>builder()
                .success(false)
                .code(errorCode.toString())
                .message(errorCode.getMessage())
                .build();
    }

    @Override
    public <T> CommonResponse<List<T>> failureList(ErrorCode errorCode) {
        return CommonResponse.<List<T>>builder()
                .success(false)
                .code(errorCode.toString())
                .message(errorCode.getMessage())
                .build();
    }
}
