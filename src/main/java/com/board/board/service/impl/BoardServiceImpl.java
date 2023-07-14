package com.board.board.service.impl;

import com.board.board.domain.Board;
import com.board.board.dto.BoardDto;
import com.board.board.repository.BoardRepository;
import com.board.board.service.BoardService;
import com.board.board.type.Category;
import com.board.board.utils.ImageUtils;
import com.board.exception.BoardException;
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
import static com.board.board.type.Status.DELETED;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Override
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

        saveThumbnailFile(thumbnail, board);
        boardRepository.save(board);

        return board.getId();
    }

    private void saveThumbnailFile(MultipartFile thumbnail, Board board) {
        if (!thumbnail.isEmpty()) {
            String storeThumbnailName = getStoreThumbnailName(thumbnail.getOriginalFilename());
            String fullPath = ImageUtils.getFullPath(storeThumbnailName);
            try {
                thumbnail.transferTo(new File(fullPath));
                board.setThumbnail(storeThumbnailName);
            } catch (IOException e) {
                throw new BoardException(ErrorCode.FILE_CANNOT_BE_PROCESSED);
            }
        }
    }

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

    @Override
    public Page<BoardDto.ListResponse> findAll(Pageable pageable, String keyword) {
        if (keyword != null && !keyword.isBlank()) {
            return boardRepository.findAllByKeyword(ACTIVE, keyword, pageable)
                    .map(BoardDto.ListResponse::fromEntity);
        }
        Page<Board> boards = boardRepository.findAllByStatus(ACTIVE, pageable);

        return boards.map(BoardDto.ListResponse::fromEntity);
    }

    @Override
    public Page<BoardDto.ListResponse> findAllByCategory(Category category, Pageable pageable, String keyword) {
        if (keyword != null && !keyword.isBlank()) {
            return boardRepository.findAllByCategoryAndKeyword(category, ACTIVE, keyword, pageable)
                    .map(BoardDto.ListResponse::fromEntity);
        }
        Page<Board> boards = boardRepository.findAllByCategoryAndStatus(category, ACTIVE, pageable);

        return boards.map(BoardDto.ListResponse::fromEntity);
    }

    @Override
    public BoardDto.DetailResponse findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardException(ErrorCode.ENTITY_NOT_FOUND));
        checkStatusOfBoard(board);

        List<Reply> replies = replyRepository.findByBoardIdAndParentIsNull(board.getId());

        return BoardDto.DetailResponse.fromEntity(board, replies);
    }

    /** 게시글이 ACTIVE 상태인지 확인 */
    private void checkStatusOfBoard(Board board) {
        if (board.getStatus() != ACTIVE) {
            throw new BoardException(ErrorCode.ENTITY_NOT_FOUND);
        }
    }

    @Override
    public Long update(BoardDto.UpdateRequest dto, MultipartFile thumbnail) {
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long id, Principal principal) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardException(ErrorCode.ENTITY_NOT_FOUND));
        Member member = memberRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new MemberException(ErrorCode.LOAD_USER_FAILED));
        checkOwnerOfBoardByMember(board, member);

        deleteRepliesOfBoard(board);
        board.setStatus(DELETED);
    }

    private void checkOwnerOfBoardByMember(Board board, Member member) {
        if (!board.getMember().getId().equals(member.getId())) {
            throw new BoardException(ErrorCode.UNAUTHENTICATED_REQUEST);
        }
    }

    /** 게시글 삭제 시에 해당 게시글에 관련된 댓글은 모두 삭제 */
    private void deleteRepliesOfBoard(Board board) {
        replyRepository.deleteAllByBoardId(board.getId());
    }
}
