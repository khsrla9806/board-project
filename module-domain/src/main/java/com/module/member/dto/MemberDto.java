package com.module.member.dto;

import com.module.member.domain.Member;
import com.module.member.type.MemberRole;
import com.module.member.type.MemberStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class MemberDto {

    private Long id;
    private String email;
    private String phone;
    private String username;
    private String nickname;
    private String password;

    private MemberRole memberRole;
    private MemberStatus memberStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime unregisteredAt;

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .phone(member.getPhone())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .memberRole(member.getMemberRole())
                .memberStatus(member.getMemberStatus())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .unregisteredAt(member.getUnregisteredAt())
                .build();
    }
}
