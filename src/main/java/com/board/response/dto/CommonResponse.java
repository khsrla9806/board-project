package com.board.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse<T> {

    private boolean success;
    private String code;
    private String message;
    private T data;
}
