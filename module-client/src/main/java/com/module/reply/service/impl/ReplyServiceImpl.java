package com.module.reply.service.impl;

import com.module.board.domain.Board;
import com.module.board.repository.BoardRepository;
import com.module.member.domain.Member;
import com.module.member.repository.MemberRepository;
import com.module.reply.domain.Reply;
import com.module.reply.dto.ReplyDto;
import com.module.reply.repository.ReplyRepository;
import com.module.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    private final MemberRepository memberRepository;

    // 특정 게시글의 정보와 댓글 출력
    @Override
    public ReplyDto.BoardWithReplyDto findBoardWithReplies(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

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
    public void addReply(ReplyDto.ReplyRequestDto replyRequestDto, Principal principal) {

        Board board = findBoard(replyRequestDto.getBoardId());

        Member member = memberRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        Reply parentReply = null;
        if (replyRequestDto.getParentId() != null) {
            parentReply = findReply(replyRequestDto.getParentId());
        }

        Reply reply = replyRequestDto.toEntity(board, parentReply, member);

        replyRepository.save(reply);
    }

    // 댓글 삭제
    @Override
    public void removeReply(Long replyId, Principal principal) {

        Reply reply = findReply(replyId);

        if(!reply.getMember().getEmail().equals(principal.getName())) {
            throw new EntityNotFoundException("일치하지 않는 회원 입니다."); // TODO: Custom Exception 처리 필요
        }else {
            replyRepository.deleteById(replyId);
        }

    }

    // 게시글이 존재하는지 검증
    private Board findBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다 : " + boardId));
    }

    // 댓글이 존재하는지 검증
    private Reply findReply(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다. : " + replyId));
    }

    // 회원이 존재하는지 검증
    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다. : " + memberId));
    }

}
