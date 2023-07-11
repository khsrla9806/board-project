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

    @GetMapping("/{category}")
    public String boardList(
            @PathVariable String category,
            Model model,
            @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardDto.ListResponse> boards;

        // TODO: 상수화
        if (category.equals("common")) {
            boards = boardService.findAllByCategory(COMMON, pageable);
            model.addAttribute("categoryTitle", "새싹 회원");
        } else if (category.equals("pro")) {
            boards = boardService.findAllByCategory(PRO, pageable);
            model.addAttribute("categoryTitle", "우수 회원");
        } else {
            throw new RuntimeException(); // TODO: 커스텀 Exception 처리
        }
        model.addAttribute("boards", boards);
        model.addAttribute("category", category);
        model.addAttribute("maxPage", 5);

        return "board/boards";
    }
}
