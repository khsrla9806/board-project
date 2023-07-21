package com.module.adminReport.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateMemberDto {

    private Long memberId;
    private String memberStatus;

}
