package com.module.global.security;

import lombok.RequiredArgsConstructor;
import com.module.member.domain.Member;
import com.module.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = findByEmail(email);
        return new CustomUserDetails(member);
    }

    private Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보를 불러오는데 실패했습니다."));
    }
}
