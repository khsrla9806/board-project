package com.board.report.service.impl;

import com.board.board.domain.Board;
import com.board.board.repository.BoardRepository;
import com.board.board.utils.ImageUtils;
import com.board.exception.BoardException;
import com.board.exception.MemberException;
import com.board.exception.ReportException;
import com.board.member.domain.Member;
import com.board.member.repository.MemberRepository;
import com.board.report.domain.Report;
import com.board.report.dto.ReportDto;
import com.board.report.repository.ReportRepository;
import com.board.report.service.ReportService;
import com.board.response.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReportRepository reportRepository;

    @Override
    @Transactional
    public void save(Long boardId, ReportDto.CreateRequest dto, MultipartFile evidenceImage, Principal principal) {
        Member member = memberRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new MemberException(ErrorCode.LOAD_USER_FAILED));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException(ErrorCode.ENTITY_NOT_FOUND));

        Report report = Report.builder()
                .reason(dto.getReason())
                .member(member)
                .board(board)
                .build();
        try {
            reportRepository.save(report);
        } catch (DataIntegrityViolationException e) {
            throw new ReportException(ErrorCode.ALREADY_REPORTED);
        }
        saveEvidenceImageFile(evidenceImage, report);
    }

    /** 신고 증거 사진 저장 (board_store 저장) */
    private void saveEvidenceImageFile(MultipartFile evidenceImage, Report report) {
        if (!evidenceImage.isEmpty()) {
            String storeName = getStoreName(evidenceImage.getOriginalFilename());
            String fullPath = ImageUtils.getFullPath(storeName);
            try {
                evidenceImage.transferTo(new File(fullPath));
                report.setEvidenceImage(storeName);
            } catch (IOException e) {
                throw new BoardException(ErrorCode.FILE_CANNOT_BE_PROCESSED);
            }
        }
    }

    private String getStoreName(String thumbnailOriginalName) {
        String uuid = UUID.randomUUID().toString();

        return uuid + thumbnailOriginalName;
    }
}
