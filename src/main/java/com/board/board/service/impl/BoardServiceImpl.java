package com.board.board.service.impl;

import com.board.board.domain.Board;
import com.board.board.dto.BoardDto;
import com.board.board.repository.BoardRepository;
import com.board.board.service.BoardService;
import com.board.board.type.Category;
import com.board.board.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.board.board.type.Category.COMMON;
import static com.board.board.type.Status.ACTIVE;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardDto.CreateRequest dto, MultipartFile thumbnail) {
        Board board = new Board();

        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setCategory(COMMON); // TODO: Member 정보 받아서 새싹, 우수 회원 확인 후 입력
        board.setStatus(ACTIVE);
        board.setMember_id(null); // TODO: Member 정보 받아서 의존 관계 형성

        if (!thumbnail.isEmpty()) {
            String storeThumbnailName = getStoreThumbnailName(thumbnail.getOriginalFilename());
            String fullPath = ImageUtils.getFullPath(storeThumbnailName);
            try {
                thumbnail.transferTo(new File(fullPath));
                board.setThumbnail(storeThumbnailName);
            } catch (IOException e) {
                throw new RuntimeException(); // TODO: 추후 CustomException 생성하여 처리
            }
        }
        boardRepository.save(board);

        return board.getId();
    }

    private String getStoreThumbnailName(String thumbnailOriginalName) {
        String uuid = UUID.randomUUID().toString();

        return uuid + thumbnailOriginalName;
    }

    public Page<BoardDto.ListResponse> findAllByCategory(Category category, Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByCategoryAndStatus(category, ACTIVE, pageable);

        return boards.map(BoardDto.ListResponse::fromEntity);
    }

    public BoardDto.DetailResponse findById(Long id) {
        return null;
    }

    public Long update(BoardDto.UpdateRequest dto, MultipartFile thumbnail) {
        return null;
    }

    public void deleteById(Long id) {

    }
}
