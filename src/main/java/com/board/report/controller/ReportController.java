package com.board.report.controller;

import com.board.exception.MemberException;
import com.board.report.domain.Report;
import com.board.report.dto.ReportDto;
import com.board.report.service.ReportService;
import com.board.report.type.Reason;
import com.board.response.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

    @PostMapping("/{boardId}")
    public String report(
            Principal principal,
            @PathVariable Long boardId,
            @Valid @ModelAttribute("report") ReportDto.CreateRequest dto,
            BindingResult bindingResult,
            MultipartFile evidenceImage,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("boardId", boardId);
            model.addAttribute("reasons", Reason.values());

            return "report/reportForm";
        }
        checkAuthentication(principal);
        reportService.save(boardId, dto, evidenceImage, principal);

        return "redirect:/";
    }

    private void checkAuthentication(Principal principal) {
        if (principal == null) {
            throw new MemberException(ErrorCode.UNAUTHENTICATED_REQUEST);
        }
    }
}
