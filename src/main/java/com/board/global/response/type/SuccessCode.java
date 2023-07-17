package com.board.global.response.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    SUCCESS("SUCCESS", "성공했습니다.")
    ;

    private final String code;
    private final String message;
}
