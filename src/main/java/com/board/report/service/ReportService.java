package com.board.report.service;

import com.board.report.dto.ReportDto;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface ReportService {
    void save(Long boardId, ReportDto.CreateRequest dto, MultipartFile thumbnail, Principal principal);
}
