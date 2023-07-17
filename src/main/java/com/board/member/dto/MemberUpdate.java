package com.board.member.dto;

import com.board.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class MemberUpdate {

    @Getter
    @Builder
    public static class Request {
        private String nickname;
        private String phone;
    }

    @Getter
    @Builder
    public static class Response {

        private String email;
        private String phone;
        private String username;
        private String nickname;

        private LocalDateTime createdAt;

        public static MemberUpdate.Response fromEntity(Member member) {
            return MemberUpdate.Response.builder()
                    .email(member.getEmail())
                    .phone(member.getPhone())
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .createdAt(member.getCreatedAt())
                    .build();
        }
    }
}
