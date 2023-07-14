package com.board.board.dto;

import com.board.board.domain.Board;
import com.board.board.type.Category;
import com.board.member.domain.Member;
import com.board.reply.domain.Reply;
import com.board.reply.dto.ReplyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

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
        private MemberDto member; // 만감한 정보는 제외

        @Getter
        @Builder
        public static class MemberDto {
            private Long id;
            private String nickname;

            public static MemberDto fromEntity(Member member) {
                return MemberDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .build();
            }
        }

        public static ListResponse fromEntity(Board board) {
            return ListResponse.builder()
                    .id(board.getId())
                    .category(board.getCategory())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .thumbnail(board.getThumbnail())
                    .member(MemberDto.fromEntity(board.getMember()))
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
        private Member member;
        private List<ReplyDto.ReplyResponseDto> replyResponseDtos;

        public static DetailResponse fromEntity(Board board, List<Reply> replies) {
            List<ReplyDto.ReplyResponseDto> replyResponseDtos = replies.stream()
                    .map(ReplyDto.ReplyResponseDto::fromEntity)
                    .collect(Collectors.toList());

            return DetailResponse.builder()
                    .id(board.getId())
                    .category(board.getCategory())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .member(board.getMember())
                    .replyResponseDtos(replyResponseDtos)
                    .build();
        }
    }
}
