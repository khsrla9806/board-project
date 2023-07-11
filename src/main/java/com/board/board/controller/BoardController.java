package com.board.board.controller;

import com.board.board.dto.BoardDto;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.board.board.type.Category.COMMON;
import static com.board.board.type.Category.PRO;

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

    @GetMapping("/common")
    public String commonBoardList(
            Model model,
            @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardDto.ListResponse> boards = boardService.findAllByCategory(COMMON, pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("categoryTitle", "새싹 회원");
        model.addAttribute("category", "common");
        model.addAttribute("maxPage", 5);

        return "board/boards";
    }

    @GetMapping("/pro")
    public String proBoardList(
            Model model,
            @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardDto.ListResponse> boards = boardService.findAllByCategory(PRO, pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("categoryTitle", "우수 회원");
        model.addAttribute("category", "pro");
        model.addAttribute("maxPage", 5);

        return "board/boards";
    }
}
