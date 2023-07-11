package com.board.board.service;

import com.board.board.dto.BoardDto;
import com.board.board.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
    Long save(BoardDto.CreateRequest dto, MultipartFile thumbnail);
    Page<BoardDto.ListResponse> findAllByCategory(Category category, Pageable pageable);
    BoardDto.DetailResponse findById(Long id);
    Long update(BoardDto.UpdateRequest dto, MultipartFile thumbnail);
    void deleteById(Long id);
}
