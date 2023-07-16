package com.board.reply.controller;

import com.board.reply.dto.ReplyDto;
import com.board.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    // MemberController 로 이동
//    @GetMapping("/{boardId}")
//    public String ListBoardAndReply(@PathVariable Long boardId, ModelMap model) {
//        ReplyDto.BoardWithReplyDto boardWithReplies = replyService.findBoardWithReplies(boardId);
//
//        model.addAttribute("board", boardWithReplies);
//        model.addAttribute("replies", boardWithReplies.getReplyResponseDtos());
//
//        return "/replies/boardAndReply";
//
//    }

    @PostMapping("/reply/new")
    public String addReply(ReplyDto.ReplyRequestDto replyRequestDto, Principal principal) {
        replyService.addReply(replyRequestDto, principal);

        return "redirect:/board/" + replyRequestDto.getBoardId();
    }

    @PostMapping("/{boardId}/reply/delete/{replyId}")
    public String removeReply(@PathVariable Long boardId, @PathVariable Long replyId, Principal principal) {

        replyService.removeReply(replyId, principal);

        return "redirect:/board/" + boardId;
    }
}
