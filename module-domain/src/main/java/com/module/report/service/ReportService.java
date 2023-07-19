package com.module.report.service;

import com.module.report.dto.ReportDto;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface ReportService {
    void save(Long boardId, ReportDto.CreateRequest dto, MultipartFile thumbnail, Principal principal);
}
