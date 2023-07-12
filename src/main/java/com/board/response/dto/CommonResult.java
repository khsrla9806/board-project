package com.board.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult {

    private boolean success; // 응답 성공 여부 T/F
    private String code; // 응답 코드
    private String message; // 응답 메세지

    public void update(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
