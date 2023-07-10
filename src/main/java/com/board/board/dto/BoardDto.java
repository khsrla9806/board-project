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

    @Getter
    @Setter
    public static class UpdateRequest {
        private Long id;
        private String title;
        private String content;
    }

    @Getter
    @Setter
    public static class ListResponse {
        private Long id;
        private String title;
        private String content;
        private String thumbnail;
        private String nickname;
    }

    @Getter
    @Setter
    public static class DetailResponse {
        private Long id;
        private String title;
        private String content;
        private String thumbnail;

        // TODO: private Member member;
        // TODO: private List<Comment> comment;
    }
}
