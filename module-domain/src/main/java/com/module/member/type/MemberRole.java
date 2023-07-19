package com.module.member.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {

    COMMON("ROLE_COMMON", "새싹회원")
    , PREMIUM("ROLE_PREMIUM", "우수회원")
    , ADMIN("ROLE_ADMIN", "관리자")
    ;

    private final String role;
    private final String description;
}
