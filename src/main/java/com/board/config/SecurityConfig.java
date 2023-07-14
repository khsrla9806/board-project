package com.board.config;

import com.board.security.CustomAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    private final CustomAuthenticationProvider authenticationProvider;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        // 권한 설정
        http.authorizeRequests()
                .antMatchers("/", "/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        // 로그인 설정
        http.formLogin()
                .loginPage("/members/login")
                .failureHandler(authenticationFailureHandler)
//                .successHandler(null)
                .permitAll();

        http.exceptionHandling()
                .accessDeniedPage("/error/denied");

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
