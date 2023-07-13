package com.board.reply.controller;

import com.board.reply.dto.ReplyDto;
import com.board.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{boardId}")
    public String ListBoardAndReply(@PathVariable Long boardId, ModelMap model) {
        ReplyDto.BoardWithReplyDto boardWithReplies = replyService.findBoardWithReplies(boardId);

        model.addAttribute("board", boardWithReplies);
        model.addAttribute("replies", boardWithReplies.getReplyResponseDtos());

        return "/replies/boardAndReply";

    }

    @PostMapping("/reply/new")
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
