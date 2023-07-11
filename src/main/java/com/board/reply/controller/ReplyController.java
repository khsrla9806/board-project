package com.board.reply.controller;

import com.board.reply.dto.ReplyDto;
import com.board.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/new")
    public String addReply(ReplyDto.ReplyRequestDto replyRequestDto) {
        replyService.addReply(replyRequestDto);

        return "redirect:/board/" + replyRequestDto.getBoardId();
    }

    @PostMapping("/{replyId}/delete")
    public String removeReply(@PathVariable Long replyId, Long boardId) {
        replyService.removeReply(replyId);

        return "redirect:/board/" + boardId;
    }
}
