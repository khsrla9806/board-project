package com.board.board.service.impl;

import com.board.board.domain.Board;
import com.board.board.dto.BoardDto;
import com.board.board.repository.BoardRepository;
import com.board.board.service.BoardService;
import com.board.board.type.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
        board.setCategory(null); // TODO: Member 정보 받아서 새싹, 우수 회원 확인 후 입력
        board.setStatus(ACTIVE);
        board.setMember_id(null); // TODO: Member 정보 받아서 의존 관계 형성

        if (!thumbnail.isEmpty()) {
            String fullPath = getThumbnailStorePath(thumbnail.getOriginalFilename());
            try {
                thumbnail.transferTo(new File(fullPath));
                board.setThumbnail(fullPath);
            } catch (IOException e) {
                throw new RuntimeException(); // TODO: 추후 CustomException 생성하여 처리
            }
        }
        boardRepository.save(board);

        return board.getId();
    }

    private String getThumbnailStorePath(String thumbnailOriginalName) {
        String uuid = UUID.randomUUID().toString();
        String homeDir = System.getProperty("user.home");
        String uploadFolderName = "board-store"; // TODO: Properties 분리

        File uploadFolder = new File(homeDir + File.separator + uploadFolderName);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdir(); // 홈 경로에 board-store 폴더가 존재하지 않으면 만든다.
        }

        // 서버를 올려서 할 것이 아니고 Local 작동하기 때문에 OS별 Home 디렉터리에 파일 생성
        return homeDir + File.separator + uploadFolderName + File.separator + uuid + thumbnailOriginalName;
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
