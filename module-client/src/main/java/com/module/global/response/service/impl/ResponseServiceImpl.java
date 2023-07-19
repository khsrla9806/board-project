package com.module.global.response.service.impl;

import com.module.response.dto.CommonResponse;
import org.springframework.stereotype.Service;
import com.module.response.service.ResponseService;
import com.module.response.type.ErrorCode;
import com.module.response.type.SuccessCode;

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
}
