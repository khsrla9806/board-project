package com.board.board.service.impl;

import com.board.board.domain.Board;
import com.board.board.dto.BoardDto;
import com.board.board.repository.BoardRepository;
import com.board.board.service.BoardService;
import com.board.board.type.Category;
import com.board.board.utils.ImageUtils;
import com.board.member.domain.Member;
import com.board.member.repository.MemberRepository;
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
        Member testSession = getSession(); // TODO: Member 세션 구현되면 이거 반드시 지우고 리팩토링 하기

        Board board = new Board();
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setCategory(COMMON); // TODO: Member 정보 받아서 새싹, 우수 회원 확인 후 입력
        board.setStatus(ACTIVE);
        board.setMember(testSession); // TODO: Member 정보 받아서 의존 관계 형성

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

    public Page<BoardDto.ListResponse> findAll(Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByStatus(ACTIVE, pageable);

        return boards.map(BoardDto.ListResponse::fromEntity);
    }

    public Page<BoardDto.ListResponse> findAllByCategory(Category category, Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByCategoryAndStatus(category, ACTIVE, pageable);

        return boards.map(BoardDto.ListResponse::fromEntity);
    }

    public BoardDto.DetailResponse findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다.")); // TODO: CustomException 변경

        return BoardDto.DetailResponse.fromEntity(board); // TODO: 댓글 사용자 정보 추가해야 함
    }

    public Long update(BoardDto.UpdateRequest dto, MultipartFile thumbnail) {
        return null;
    }

    public void deleteById(Long id) {

    }


    /* 여기 부터 지워야하는 테스트 영역입니다. */
    private final MemberRepository memberRepository;

    public Member getSession() {
        return memberRepository.findById(1L).orElse(null);
    }
    /* 여기 까지 지워야하는 테스트 영역입니다. */
}
