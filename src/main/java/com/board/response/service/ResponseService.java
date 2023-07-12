package com.board.response.service;

import com.board.response.dto.CommonResult;
import com.board.response.dto.ListResult;
import com.board.response.dto.SingleResult;
import com.board.response.type.ErrorCode;

import java.util.List;

public interface ResponseService {

    <T> SingleResult<T> getSingleResult(T data);

    <T> ListResult<T> getListResult(List<T> list);

    CommonResult getSuccessResult();

    CommonResult getFailResult(ErrorCode errorCode);

}
