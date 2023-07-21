package com.module.adminReport.dto;

import com.module.member.type.MemberRole;
import com.module.member.type.MemberStatus;
import com.module.report.type.Reason;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminReportDto {

    private Long id;
    private String reporterNickname;
    private LocalDateTime createdAt;
    private Reason reason;
    private String evidenceImage;
    private Long reportedUserId;
    private String nickname;
    private String username;
    private String email;
    private MemberRole memberRole;
    private MemberStatus memberStatus;


}
