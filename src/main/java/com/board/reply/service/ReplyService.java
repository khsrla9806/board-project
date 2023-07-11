package com.board.reply.service;

import com.board.reply.dto.ReplyDto;

public interface ReplyService {

    void addReply(ReplyDto.ReplyRequestDto replyRequestDto);

    void removeReply(Long id);

}
