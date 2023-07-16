package com.board.report.controller;

import com.board.exception.MemberException;
import com.board.report.dto.ReportDto;
import com.board.report.service.ReportService;
import com.board.report.type.Reason;
import com.board.response.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/report")
@Controller
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reportForm/{boardId}")
    public String reportForm(
            Principal principal,
            @PathVariable Long boardId,
            Model model
    ) {
        checkAuthentication(principal);
        model.addAttribute("boardId", boardId);
        model.addAttribute("reasons", Reason.values());
        model.addAttribute("report", ReportDto.CreateRequest.builder().build());

        return "report/reportForm";
    }

    private void checkAuthentication(Principal principal) {
        if (principal == null) {
            throw new MemberException(ErrorCode.UNAUTHENTICATED_REQUEST);
        }
    }
}
