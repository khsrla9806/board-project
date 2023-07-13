package com.board.reply.dto;

import com.board.board.domain.Board;
import com.board.reply.domain.Reply;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReplyDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    @Builder
    public static class ReplyRequestDto {

        @NotNull
        private Long boardId;

        private Long parentId;

        @NotNull
        private Long memberId;      // TODO : Member 와 연관관계 설정

        @NotBlank
        private String content;

        public Reply toEntity(Board board, Reply parenReply, Long userId) {
            return Reply.builder()
                    .content(content)
                    .parent(parenReply)
                    .board(board)
                    .memberId(userId)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ReplyResponseDto {
        private Long id;
        private String content;
        private Long parentId;
        private Long boardId;
        private Long memberId;
        private List<ReplyResponseDto> children;

        public static ReplyResponseDto fromEntity(Reply reply) {
            List<ReplyResponseDto> children = new ArrayList<>();

            if (reply.getChildren() != null) {
                for (Reply child : reply.getChildren()) {
                    children.add(fromEntity(child));
                }
            }

            return ReplyResponseDto.builder()
                    .id(reply.getId())
                    .content(reply.getContent())
                    .parentId(
                            reply.getParent() != null ? reply.getParent().getId() : null
                    )
                    .boardId(reply.getBoard().getId())
                    .memberId(reply.getMemberId())
                    .children(children)
                    .build();

        }

    }

    @Getter
    @Builder
    @ToString
    public static class BoardWithReplyDto {
        private Long id;
        private Long memberId;
        private LocalDateTime createdAt;
        private String title;
        private String content;
        private String thumbnail;
        private List<ReplyResponseDto> replyResponseDtos;

    }


}
