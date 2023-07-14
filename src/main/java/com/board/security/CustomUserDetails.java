package com.board.security;

import com.board.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static com.board.member.type.MemberStatus.ACTIVE;
import static com.board.member.type.MemberStatus.BLOCKED;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Member member;

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getMemberRole().getRole());
        return Collections.singleton(grantedAuthority);
    }

    // 사용자 암호화된 비밀번호를 반환
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // 사용자 고유 식별자인(이메일) 반환
    @Override
    public String getUsername() {
        return member.getEmail();
    }

    // 사용자 계정 만료여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return member.getMemberStatus() != BLOCKED;
    }

    // 사용자 인증 정보(비밀번호) 만료 여부를 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자 계정 활성화 가능여부 확인
    @Override
    public boolean isEnabled() {
        return member.getMemberStatus() == ACTIVE;
    }
}
