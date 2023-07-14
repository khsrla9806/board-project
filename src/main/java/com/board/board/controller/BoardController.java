package com.board.board.controller;

import com.board.board.domain.Board;
import com.board.board.dto.BoardDto;
import com.board.board.repository.BoardRepository;
import com.board.board.service.BoardService;
import com.board.board.type.BoardView;
import com.board.board.utils.ImageUtils;
import com.board.exception.BoardException;
import com.board.response.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.net.MalformedURLException;
import java.security.Principal;

import static com.board.board.type.Category.COMMON;
import static com.board.board.type.Category.PRO;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping("/createForm")
    public String boardCreateForm(Principal principal, Model model) {
        checkAuthentication(principal);
        model.addAttribute("board", new BoardDto.CreateRequest());

        return "board/createForm";
    }

    @PostMapping
    public String createBoard(
            Principal principal,
            @Valid @ModelAttribute("board") BoardDto.CreateRequest dto,
            BindingResult bindingResult,
            @RequestParam MultipartFile thumbnail
    ) {
        checkAuthentication(principal);
        if (bindingResult.hasErrors()) {
            return "board/createForm";
        }

        Long boardId = boardService.save(dto, thumbnail, principal);

        return "redirect:/board/" + boardId;
    }

    @GetMapping
    public String boardList(
            Model model,
            @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @Nullable @RequestParam String keyword
    ) {
        Page<BoardDto.ListResponse> boards = boardService.findAll(pageable, keyword);
        model.addAttribute("boards", boards);
        model.addAttribute("categoryTitle", BoardView.ALL);
        model.addAttribute("maxPage", 5);

        return "board/boards";
    }

    @GetMapping("/common")
    public String commonBoardList(
            Model model,
            @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @Nullable @RequestParam String keyword
    ) {
        Page<BoardDto.ListResponse> boards = boardService.findAllByCategory(COMMON, pageable, keyword);
        model.addAttribute("boards", boards);
        model.addAttribute("categoryTitle", BoardView.COMMON);
        model.addAttribute("maxPage", 5);

        return "board/boards";
    }

    @GetMapping("/pro")
    public String proBoardList(
            Model model,
            @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @Nullable @RequestParam String keyword
    ) {
        Page<BoardDto.ListResponse> boards = boardService.findAllByCategory(PRO, pageable, keyword);
        model.addAttribute("boards", boards);
        model.addAttribute("categoryTitle", BoardView.PRO);
        model.addAttribute("maxPage", 5);

        return "board/boards";
    }

    @GetMapping("/{id}")
    public String boardDetail(@PathVariable Long id, Model model) {
        BoardDto.DetailResponse boardDetailResponse = boardService.findById(id);
        String category = getBoardCategory(boardDetailResponse);
        model.addAttribute("boardCategory", category);
        model.addAttribute("board", boardDetailResponse);

        return "board/board";
    }

    private String getBoardCategory(BoardDto.DetailResponse boardDetailResponse) {
        String category = boardDetailResponse.getCategory().name();
        if (category.equals(COMMON.name())) {
            return COMMON.getCategoryTitle();
        }
        return PRO.getCategoryTitle();
    }

    @PostMapping("/delete/{id}")
    public String deleteBoard(Principal principal, @PathVariable Long id) {
        checkAuthentication(principal);
        boardService.deleteById(id, principal);

        return "redirect:/";
    }

    @GetMapping("/updateForm/{id}")
    public String boardUpdateForm(
            Principal principal,
            Model model,
            @PathVariable Long id
    ) {
        checkAuthentication(principal);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardException(ErrorCode.ENTITY_NOT_FOUND));
        model.addAttribute("board", board);

        return "board/updateForm";
    }

    @PostMapping("/update")
    public String updateBoard(
            Principal principal,
            @Valid @ModelAttribute("board") BoardDto.UpdateRequest dto,
            BindingResult bindingResult,
            @RequestParam MultipartFile thumbnail
    ) {
        checkAuthentication(principal);
        if (bindingResult.hasErrors()) {
            return "board/updateForm";
        }

        Long boardId = boardService.update(dto, thumbnail, principal);

        return "redirect:/board/" + boardId;
    }
  
    @ResponseBody
    @GetMapping("/image/{filename}")
    public Resource thumbnailImage(@PathVariable String filename) throws MalformedURLException {
        if (filename.equals(BoardView.NULL)) {
            return new UrlResource(BoardView.DEFAULT_IMAGE_URL);
        }
        String fullPath = ImageUtils.getFullPath(filename);

        return new UrlResource("file:" + fullPath);
    }

    private void checkAuthentication(Principal principal) {
        if (principal == null) {
            throw new BoardException(ErrorCode.UNAUTHENTICATED_REQUEST);
        }
    }
}
