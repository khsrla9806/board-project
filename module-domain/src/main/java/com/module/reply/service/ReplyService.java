package com.module.reply.service;


import com.module.reply.dto.ReplyDto;

import java.security.Principal;

public interface ReplyService {

    ReplyDto.BoardWithReplyDto findBoardWithReplies(Long boardId);

    void addReply(ReplyDto.ReplyRequestDto replyRequestDto, Principal principal);

    void removeReply(Long replyId, Principal principal);

}
