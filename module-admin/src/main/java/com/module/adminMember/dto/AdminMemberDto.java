package com.module.adminMember.dto;

import com.module.member.type.MemberRole;
import com.module.member.type.MemberStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminMemberDto {

    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private MemberRole memberRole;
    private MemberStatus memberStatus;
    private LocalDateTime createdAt;
    private int boardCount;
    private int replyCount;

}
