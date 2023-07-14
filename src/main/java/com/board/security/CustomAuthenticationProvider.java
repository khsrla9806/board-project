package com.board.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails member = userDetailsService.loadUserByUsername(email);

        System.out.println("CustomAuthenticationProvider::email = " + email);
        System.out.println("CustomAuthenticationProvider::password = " + password);
        System.out.println("member = " + member);

        // TODO 에러 공통처리 필요
        if (member == null) {
//            throw new MemberException(LOAD_USER_FAILED);
            throw new InternalAuthenticationServiceException("");
        }
        if (!member.isAccountNonExpired()) {
            throw new LockedException("만료된 계정입니다.");
        }
        if (!member.isAccountNonLocked()) {
            throw new LockedException("정지된 회원입니다.(블랙리스트)");
        }
        if (!member.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("회원 인증 정보가 만료되었습니다.");
        }
        if(!member.isEnabled()){
            throw new DisabledException("활성화되지 않은 회원입니다.");
        }
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(email, password, member.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
