package com.board.board.dto;

import lombok.Getter;
import lombok.Setter;

public class BoardDto {

    @Getter
    @Setter
    public static class CreateRequest {
        private String title;
        private String content;
    }
}
