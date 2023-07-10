package com.board.board.controller;

import com.board.board.dto.BoardDto;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/createForm")
    public String boardCreateForm(Model model) {
        model.addAttribute("board", new BoardDto.CreateRequest());

        return "board/createForm";
    }

    @PostMapping
    public String createBoard(
            @Valid @ModelAttribute("board") BoardDto.CreateRequest dto,
            BindingResult bindingResult,
            @RequestParam MultipartFile thumbnail
    ) {
        if (bindingResult.hasErrors()) {
            return "board/createForm";
        }

        Long boardId = boardService.save(dto, thumbnail);

        return "redirect:/board/" + boardId;
    }
}
