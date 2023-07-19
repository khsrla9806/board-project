package com.module.global.config;

import com.module.global.security.CustomAuthenticationFailureHandler;
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
                .failureUrl("/members/login")
                .defaultSuccessUrl("/board")
                .failureHandler(authenticationFailureHandler)
//                .successHandler(null)
                .usernameParameter("email")
                .permitAll();

        // 로그아웃 설정
        http.logout()
                .logoutUrl("/members/logout")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/members/login")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("SESSION", "JSESSIONID");

        // 세션 설정
//        http.sessionManagement()
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(false)
//                .and()
//                .sessionFixation().changeSessionId();

        http.exceptionHandling()
                .accessDeniedPage("/error/denied");

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
