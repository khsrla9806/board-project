package com.module.adminReport.service.impl;

import com.module.adminReport.dto.AdminReportDto;
import com.module.adminReport.repository.AdminReportRepository;
import com.module.adminReport.service.AdminReportService;
import com.module.member.type.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminReportServiceImpl implements AdminReportService {

    private final AdminReportRepository adminReportRepository;

    @Override
    public List<AdminReportDto> findAllReportWithMemberAndBoard() {
        return adminReportRepository.findAllReportWithMemberAndBoard();
    }

    @Override
    public List<AdminReportDto> findAllReportWithMemberAndBoardByReason(String reason) {
        return adminReportRepository.findAllReportWithMemberAndBoardByReason(reason);
    }

    @Override
    public void updateMemberStatus(Long memberId, MemberStatus memberStatus) {
        adminReportRepository.updateMemberStatus(memberId, memberStatus);
    }
}
