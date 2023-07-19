package com.module.member.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatus {

    ACTIVE("활성화된 계정")
    , INACTIVE("비활성화된 계정")
    , BLOCKED("정지된 계정")
    , WITHDRAWN("탈퇴된 계정")
    ;

    private final String description;
}
