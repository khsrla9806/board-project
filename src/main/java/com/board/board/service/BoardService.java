package com.board.board.service;

import com.board.board.dto.BoardDto;
import com.board.board.type.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    Long save(BoardDto.CreateRequest dto, MultipartFile thumbnail);
    List<BoardDto.ListResponse> findAllByCategory(Category category); // TODO: Pageable
    BoardDto.DetailResponse findById(Long id);
    Long update(BoardDto.UpdateRequest dto, MultipartFile thumbnail);
    void deleteById(Long id);
}
