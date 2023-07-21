package com.module.admin.board.controller;

import com.module.admin.board.service.AdminBoardService;
import com.module.response.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBoardController {

    private final AdminBoardService boardService;

    @GetMapping("/members/{memberId}/boards")
    public String getMemberBoardsOrderByCreatedAtDesc(@PathVariable String memberId, Model model) {
        CommonResponse<?> response = boardService.findBoardsByMemberIdOrderByCreatedAtDesc(Long.valueOf(memberId));
        model.addAttribute("response", response);
        return "admin/boards";
    }
}
