package com.board.member.service;

import com.board.member.dto.MemberRegistration;
import com.board.response.dto.CommonResponse;

public interface MemberService {

    // 회원 가입
    CommonResponse<Long> register(MemberRegistration parameter);

    // 이메일 인증
    CommonResponse<?> authConfirm(Long id, String emailAuthToken);

    // 이름 유효성 확인
    CommonResponse<?> validateUsername(String username);

    // 닉네임 유효성 확인
    CommonResponse<?> validateNickname(String nickname);

    // 이메일 유효성 확인
    CommonResponse<?> validateEmail(String email);
    
    // 전화번호 유효성 확인
    CommonResponse<?> validatePhone(String phone);

    // 비밀번호 유효성 확인
    CommonResponse<?> validatePassword(String password);

    // 비밀번호 일치 확인
    CommonResponse<?> validateConfirmPassword(String password, String confirmPassword);
}
