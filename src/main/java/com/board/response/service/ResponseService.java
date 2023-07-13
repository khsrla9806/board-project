package com.board.response.service;

import com.board.response.dto.CommonResponse;
import com.board.response.type.ErrorCode;
import com.board.response.type.SuccessCode;

import java.util.List;

public interface ResponseService {

    <T> CommonResponse<T> success(T data, SuccessCode successCode);

    <T> CommonResponse<List<T>> successList(List<T> data, SuccessCode successCode);

    CommonResponse<?> successWithNoContent(SuccessCode successCode);

    <T> CommonResponse<T> failure(ErrorCode errorCode);

    <T> CommonResponse<List<T>> failureList(ErrorCode errorCode);

}
