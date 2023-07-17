package com.board.member.service;

import com.board.global.response.dto.CommonResponse;
import com.board.member.dto.MemberRegistration;
import com.board.member.dto.MemberUpdate;
import com.board.member.dto.PasswordUpdate;

public interface MemberService {

    // 회원 가입
    CommonResponse<Long> register(MemberRegistration parameter);

    // 이메일 인증
    CommonResponse<?> authConfirm(Long id, String emailAuthToken);

    // 마이페이지 확인
    CommonResponse<?> getMemberDetailsByEmail(String email);

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

    // 입력받은 비밀번호와 DB에 저장된 비밀번호 일치 확인
    CommonResponse<?> validateCurrentPassword(String email, String password);

    // 회원 정보 변경
    CommonResponse<?> updateMember(String email, MemberUpdate.Request request);

    // 비밀번호 변경
    CommonResponse<?> updatePassword(String email, PasswordUpdate.Request request);
}
