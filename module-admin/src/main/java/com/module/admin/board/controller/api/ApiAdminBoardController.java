package com.module.admin.board.controller.api;

import com.module.admin.board.dto.UpdateBoardStatus;
import com.module.admin.board.service.AdminBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ApiAdminBoardController {

    private final AdminBoardService boardService;

    @PostMapping("/updateBoardStatus")
    public ResponseEntity<?> updateBoardStatus(@ModelAttribute UpdateBoardStatus request) {
        String boardId = request.getBoardId();
        String status = request.getStatus();

        return ResponseEntity.ok(boardService.updateBoardStatus(Long.valueOf(boardId), status));
    }

//    @PostMapping("/updateBoardStatus")
//    public ResponseEntity<?> updateBoardStatus(@RequestParam String boardId, @RequestParam String status) {
//        return ResponseEntity.ok(boardService.updateBoardStatus(Long.valueOf(boardId), status));
//    }

}
