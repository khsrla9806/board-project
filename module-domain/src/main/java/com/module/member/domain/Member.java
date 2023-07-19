package com.module.member.domain;

import com.module.global.BaseEntity;
import com.module.member.dto.MemberUpdate;
import com.module.member.type.MemberRole;
import com.module.member.type.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member extends BaseEntity {

    @Column(nullable = false)
    private String email;
    @Column(nullable = false, unique = true)
    private String phone;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @Column
    private LocalDateTime unregisteredAt;

    public void updateMemberStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    public void updateMember(MemberUpdate.Request request) {
        this.nickname = request.getNickname();
        this.phone = request.getPhone();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}