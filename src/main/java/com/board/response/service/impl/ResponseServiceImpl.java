package com.board.response.service.impl;

import com.board.response.dto.CommonResult;
import com.board.response.dto.ListResult;
import com.board.response.dto.SingleResult;
import com.board.response.service.ResponseService;
import com.board.response.type.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.board.response.type.SuccessCode.SUCCESS;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Override
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = SingleResult.<T>builder()
                .data(data)
                .build();

        result.update(true, SUCCESS.getCode(), SUCCESS.getMessage());
        return result;
    }

    @Override
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = ListResult.<T>builder()
                .data(list)
                .build();

        result.update(true, SUCCESS.getCode(), SUCCESS.getMessage());
        return result;
    }

    @Override
    public CommonResult getSuccessResult() {
        return new CommonResult(true, SUCCESS.getCode(), SUCCESS.getMessage());
    }

    @Override
    public CommonResult getFailResult(ErrorCode errorCode) {
        return new CommonResult(false, errorCode.toString(), errorCode.getMessage());
    }

}
