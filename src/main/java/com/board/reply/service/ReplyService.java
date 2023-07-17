package com.board.reply.service;

import com.board.reply.dto.ReplyDto;

import java.security.Principal;

public interface ReplyService {

    ReplyDto.BoardWithReplyDto findBoardWithReplies(Long boardId);

    void addReply(ReplyDto.ReplyRequestDto replyRequestDto, Principal principal);

    void removeReply(Long replyId, Principal principal);

}
