package com.module.board.service;

import com.module.board.dto.BoardDto;
import com.module.board.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface BoardService {
    Long save(BoardDto.CreateRequest dto, MultipartFile thumbnail, Principal principal);
    Page<BoardDto.ListResponse> findAll(Pageable pageable, String keyword);
    Page<BoardDto.ListResponse> findAllByCategory(Category category, Pageable pageable, String keyword);
    BoardDto.DetailResponse findById(Long id);
    Long update(BoardDto.UpdateRequest dto, MultipartFile thumbnail, Principal principal);
    void deleteById(Long id, Principal principal);
}
