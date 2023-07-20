package com.module.admin.transfer.dto;

import com.module.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailTransfer {

    private String title;
    private String content;
    private Member sender;
    private Member receiver;
}
