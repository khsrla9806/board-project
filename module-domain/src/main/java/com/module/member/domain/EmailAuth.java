package com.module.member.domain;

import com.module.global.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.module.member.type.EmailAuthStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.module.member.type.EmailAuthStatus.UNVERIFIED;
import static com.module.member.type.EmailAuthStatus.VERIFIED;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class EmailAuth extends BaseEntity {

    @Column(nullable = false)
    private String emailAuthToken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmailAuthStatus emailAuthStatus;

    @Column
    private LocalDateTime emailAuthAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;

    public EmailAuth confirmAuth() {
        this.emailAuthStatus = VERIFIED;
        this.emailAuthAt = LocalDateTime.now();
        return this;
    }

    public static EmailAuth generateEmailAuth(Member member) {
        return EmailAuth.builder()
                .emailAuthToken(UUID.randomUUID().toString())
                .emailAuthStatus(UNVERIFIED)
                .member(member)
                .build();
    }
}
