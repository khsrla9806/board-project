package com.board.reply.dto;

import com.board.reply.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ReplyDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ReplyRequestDto {

        private Long id;

        @NotNull
        private Long boardId;       // TODO : Board 와 연관관계 설정

        @NotNull
        private Long memberId;      // TODO : Member 와 연관관계 설정

        @NotBlank
        private String content;

        public Reply toEntity() {
            return Reply.builder()
                    .id(id)
                    .boardId(boardId)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ReplyResponseDto {
        private Long id;
        private String content;
        private Long boardId;
        private Long memberId;
    }

}
