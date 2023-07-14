package com.board.board.service.impl;

import com.board.board.domain.Board;
import com.board.board.dto.BoardDto;
import com.board.board.repository.BoardRepository;
import com.board.board.service.BoardService;
import com.board.board.type.Category;
import com.board.board.utils.ImageUtils;
import com.board.exception.MemberException;
import com.board.member.domain.Member;
import com.board.member.repository.MemberRepository;
import com.board.member.type.MemberRole;
import com.board.reply.domain.Reply;
import com.board.reply.repository.ReplyRepository;
import com.board.response.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static com.board.board.type.Category.COMMON;
import static com.board.board.type.Category.PRO;
import static com.board.board.type.Status.ACTIVE;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(BoardDto.CreateRequest dto, MultipartFile thumbnail, Principal principal) {
        Member member = memberRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new MemberException(ErrorCode.LOAD_USER_FAILED));

        Board board = new Board();
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setCategory(getCategoryByMember(member));
        board.setStatus(ACTIVE);
        board.setMember(member);

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
    private Category getCategoryByMember(Member member) {
        if (member.getMemberRole() == MemberRole.COMMON) {
            return COMMON;
        }
        return PRO;
    }

    private String getStoreThumbnailName(String thumbnailOriginalName) {
        String uuid = UUID.randomUUID().toString();

        return uuid + thumbnailOriginalName;
    }

    public Page<BoardDto.ListResponse> findAll(Pageable pageable, String keyword) {
        if (keyword != null && !keyword.isBlank()) {
            return boardRepository.findAllByKeyword(ACTIVE, keyword, pageable)
                    .map(BoardDto.ListResponse::fromEntity);
        }
        Page<Board> boards = boardRepository.findAllByStatus(ACTIVE, pageable);

        return boards.map(BoardDto.ListResponse::fromEntity);
    }

    public Page<BoardDto.ListResponse> findAllByCategory(Category category, Pageable pageable, String keyword) {
        if (keyword != null && !keyword.isBlank()) {
            return boardRepository.findAllByCategoryAndKeyword(category, ACTIVE, keyword, pageable)
                    .map(BoardDto.ListResponse::fromEntity);
        }
        Page<Board> boards = boardRepository.findAllByCategoryAndStatus(category, ACTIVE, pageable);

        return boards.map(BoardDto.ListResponse::fromEntity);
    }

    public BoardDto.DetailResponse findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다.")); // TODO: CustomException 변경

        List<Reply> replies = replyRepository.findByBoardIdAndParentIsNull(board.getId());

        return BoardDto.DetailResponse.fromEntity(board, replies); // TODO: 댓글 사용자 정보 추가해야 함
    }

    public Long update(BoardDto.UpdateRequest dto, MultipartFile thumbnail) {
        return null;
    }

    public void deleteById(Long id) {

    }
}
