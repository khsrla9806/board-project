package com.module.admin.board.dto;

import com.module.board.domain.Board;
import com.module.board.type.Category;
import com.module.board.type.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminBoardDto {
    private Long id;
    private String title;
    private Category category;
    private Status status;
    private LocalDateTime createdAt;

    public static AdminBoardDto fromEntity(Board board) {
        return AdminBoardDto.builder()
                .id(board.getId())
                .title(builder().title)
                .category(board.getCategory())
                .status(board.getStatus())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
