package com.board.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class BoardDto {

    @Getter
    @Setter
    public static class CreateRequest {
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        @Length(max = 255, message = "제목은 255자를 넘어갈 수 없습니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 값입니다.")
        @Length(max = 20000, message = "본문은 20,000자를 넘어갈 수 없습니다.")
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
