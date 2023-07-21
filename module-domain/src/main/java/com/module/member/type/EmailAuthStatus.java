package com.module.member.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailAuthStatus {

    VERIFIED("이메일 인증")
    , UNVERIFIED("이메일 미인증")
    ;

    private final String description;
}
