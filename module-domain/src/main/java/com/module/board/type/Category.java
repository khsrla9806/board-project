package com.module.board.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    COMMON("[새싹 회원 게시판]"),
    PRO("[우수 회원 게시판]");

    private final String categoryTitle;
}
