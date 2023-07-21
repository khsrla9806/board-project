package com.module.adminReport.repository;

import com.module.adminReport.dto.AdminReportDto;
import com.module.member.type.MemberStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminReportRepository {

    List<AdminReportDto> findAllReportWithMemberAndBoard();

    List<AdminReportDto> findAllReportWithMemberAndBoardByReason(String reason);

    void updateMemberStatus(Long memberId, MemberStatus memberStatus);


}
