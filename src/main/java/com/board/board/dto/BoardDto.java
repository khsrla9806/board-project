package com.board.board.dto;

import com.board.board.domain.Board;
import com.board.board.type.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    @AllArgsConstructor
    @Builder
    public static class ListResponse {
        private Long id;
        private Category category;
        private String title;
        private String content;
        private String thumbnail;
        private String nickname;

        public static ListResponse fromEntity(Board board) {
            return ListResponse.builder()
                    .id(board.getId())
                    .category(board.getCategory())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .thumbnail(board.getThumbnail())
                    // TODO: Member 연결되면 리펙토링
                    .nickname(board.getMember() == null ? "없는 사용자" : board.getMember().getNickname())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class DetailResponse {
        private Long id;
        private Category category;
        private String title;
        private String content;
        private String nickname;

        // TODO: Member 연결되면 변경 (id, nickname 갖는 DTO 하나 추가)
        // TODO: 대댓글 구현 완료되면 (List<ReplyDto> 추가)

        public static DetailResponse fromEntity(Board board) {
            return DetailResponse.builder()
                    .id(board.getId())
                    .category(board.getCategory())
                    .title(board.getTitle())
                    .content(board.getContent())
                    // TODO: Member 연결되면 리펙토링
                    .nickname(board.getMember() == null ? "없는 사용자" : board.getMember().getNickname())
                    .build();
        }
    }
}
