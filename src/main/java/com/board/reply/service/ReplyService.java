package com.board.reply.service;

import com.board.reply.dto.ReplyDto;

public interface ReplyService {

    ReplyDto.BoardWithReplyDto findBoardWithReplies(Long boardId);

    void addReply(ReplyDto.ReplyRequestDto replyRequestDto);

    void removeReply(Long id);

}
