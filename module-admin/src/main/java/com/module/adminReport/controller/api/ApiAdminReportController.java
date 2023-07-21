package com.module.adminReport.controller.api;

import com.module.adminReport.dto.AdminReportDto;
import com.module.adminReport.dto.UpdateMemberDto;
import com.module.adminReport.service.AdminReportService;
import com.module.member.type.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ApiAdminReportController {

    private final AdminReportService adminReportService;

    @GetMapping("/reports")
    public ResponseEntity<List<AdminReportDto>> reports(@RequestParam(value = "reason", defaultValue = "") String reason) {

        List<AdminReportDto> response = adminReportService.findAllReportWithMemberAndBoardByReason(reason);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/report/member/update")
    public ResponseEntity<String> updateMemberStatus(@RequestBody UpdateMemberDto updateMemberDto) {

        try {
            MemberStatus memberStatus = MemberStatus.valueOf(updateMemberDto.getMemberStatus());
            adminReportService.updateMemberStatus(updateMemberDto.getMemberId(), memberStatus);
            return ResponseEntity.ok("회원 상태가 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            // Enum 값 파싱에 실패한 경우 (잘못된 상태 값이 들어온 경우)
            return ResponseEntity.badRequest().body("잘못된 회원 상태 값입니다.");
        } catch (Exception e) {
            // 다른 예외가 발생한 경우 (예를 들어 데이터베이스 오류 등)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

}
