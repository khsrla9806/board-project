package com.board.reply.service.impl;

import com.board.board.domain.Board;
import com.board.board.repository.BoardRepository;
import com.board.reply.domain.Reply;
import com.board.reply.dto.ReplyDto;
import com.board.reply.repository.ReplyRepository;
import com.board.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    // 특정 게시글의 댓글 출력
    @Override
    public ReplyDto.BoardWithReplyDto findBoardWithReplies(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다 : " + boardId));

        List<Reply> replies = replyRepository.findByBoardIdAndParentIsNull(board.getId());

        List<ReplyDto.ReplyResponseDto> replyResponseDtos = replies.stream()
                .map(ReplyDto.ReplyResponseDto::fromEntity)
                .collect(Collectors.toList());

        return ReplyDto.BoardWithReplyDto.builder()
                .id(board.getId())
                .memberId(board.getMember().getId())
                .createdAt(board.getCreatedAt())
                .title(board.getTitle())
                .content(board.getContent())
                .thumbnail(board.getThumbnail())
                .replyResponseDtos(replyResponseDtos)
                .build();
    }

    // 댓글 등록
    @Override
    public void addReply(ReplyDto.ReplyRequestDto replyRequestDto) {

        Board board = boardRepository.findById(replyRequestDto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다 : " + replyRequestDto.getBoardId()));

        Reply parenReply = null;
        if (replyRequestDto.getParentId() != null) {
            parenReply = replyRepository.findById(replyRequestDto.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다. : " + replyRequestDto.getParentId()));
        }

        Long userId = 1L;   // TODO : Member 와 연관관계 설정 현재는 임시로 1L 값을 넣기로 함

        Reply reply = replyRequestDto.toEntity(board, parenReply, userId);

        replyRepository.save(reply);
    }

    // 댓글 삭제
    @Override
    public void removeReply(Long id) {

        replyRepository.deleteById(id);

    }
}
