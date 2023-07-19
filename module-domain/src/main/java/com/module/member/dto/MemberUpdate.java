package com.module.member.dto;

import com.module.member.domain.Member;
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

        public static Response fromEntity(Member member) {
            return Response.builder()
                    .email(member.getEmail())
                    .phone(member.getPhone())
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .createdAt(member.getCreatedAt())
                    .build();
        }
    }
}
