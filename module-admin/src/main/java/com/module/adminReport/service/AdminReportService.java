package com.module.adminReport.service;

import com.module.adminReport.dto.AdminReportDto;
import com.module.member.type.MemberStatus;

import java.util.List;

public interface AdminReportService {

    List<AdminReportDto> findAllReportWithMemberAndBoard();

    List<AdminReportDto> findAllReportWithMemberAndBoardByReason(String reason);

    void updateMemberStatus(Long memberId, MemberStatus memberStatus);

}
