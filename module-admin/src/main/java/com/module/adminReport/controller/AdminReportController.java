package com.module.adminReport.controller;

import com.module.adminReport.dto.AdminReportDto;
import com.module.adminReport.service.AdminReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminReportController {

    private final AdminReportService adminReportService;

    @GetMapping("/reports")
    public String reports(Model model) {
        List<AdminReportDto> allReports = adminReportService.findAllReportWithMemberAndBoard();
        model.addAttribute("response", allReports);
        return "/admin/report";
    }

    @ResponseBody
    @GetMapping("/report/image/{filename}")
    public Resource findEvidenceImage(@PathVariable String filename) throws MalformedURLException {

        String homeDir = System.getProperty("user.home");
        String uploadFolderName = "board-store";

        String fullPath = homeDir + File.separator + uploadFolderName + File.separator + filename;

        return new UrlResource("file:" + fullPath);
    }


}
