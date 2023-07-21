package com.module.admin.board.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardStatus {

    private String boardId;
    private String status;
}
