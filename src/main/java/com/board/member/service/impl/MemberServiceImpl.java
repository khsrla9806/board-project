package com.board.member.service.impl;

import com.board.components.MailComponents;
import com.board.exception.MemberException;
import com.board.member.domain.EmailAuth;
import com.board.member.domain.Member;
import com.board.member.dto.MemberRegistration;
import com.board.member.repository.EmailAuthRepository;
import com.board.member.repository.MemberRepository;
import com.board.member.service.MemberService;
import com.board.response.dto.CommonResponse;
import com.board.response.service.ResponseService;
import com.board.response.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.board.member.type.MemberRole.COMMON;
import static com.board.member.type.MemberStatus.ACTIVE;
import static com.board.member.type.MemberStatus.INACTIVE;
import static com.board.response.type.ErrorCode.*;
import static com.board.response.type.SuccessCode.SUCCESS;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final EmailAuthRepository emailAuthRepository;
    private final ResponseService responseService;

    private final PasswordEncoder passwordEncoder;

    private final MailComponents mailComponents;

    @Override
    @Transactional
    public CommonResponse<Long> register(MemberRegistration request) {
        // 1. 유효성 검사(닉네임, 이메일, 전화번호 중복 확인)
        validateNicknameNotExist(request.getNickname());
        validateEmailNotExist(request.getEmail());
        validatePhoneNotExist(request.getPhone());

        // 2. 비밀번호 일치 여부 확인
        validatePasswordMatch(request.getPassword(), request.getConfirmPassword());

        // 3. 회원 정보 저장
        Member member = memberRepository.save(Member.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .username(request.getUsername())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .memberRole(COMMON)
                .memberStatus(INACTIVE)
                .build());

        // 4. 인증 이메일 전송
        EmailAuth emailAuth = emailAuthRepository.save(EmailAuth.generateEmailAuth(member));
        sendAuthConfirmEmail(member, emailAuth);

        return responseService.success(member.getId(), SUCCESS);
    }

    @Override
    @Transactional
    public CommonResponse<?> authConfirm(Long id, String emailAuthToken) {
        // 1. 유효성 검사(인증 정보, 토큰 일치 여부 확인)
        EmailAuth emailAuth = getEmailAuthById(id);
        validateEmailAuthTokenMatch(emailAuth, emailAuthToken);
        
        // 2. 이메일 인증 완료
        emailAuthRepository.save(emailAuth.confirmAuth());
        
        // 3. 계정 활성화
        Member member = emailAuth.getMember();
        member.updateMemberStatus(ACTIVE);
        memberRepository.save(member);

        return responseService.successWithNoContent(SUCCESS);
    }

    @Override
    public CommonResponse<?> validateUsername(String username) {
        // 1. 유효성 검사(이름 패턴 확인)
        String usernamePattern = "^[가-힣]{2,3}$";
        validatePatternMatch(usernamePattern, username, INVALID_USERNAME);

        return responseService.successWithNoContent(SUCCESS);
    }

    @Override
    public CommonResponse<?> validateNickname(String nickname) {
        // 1. 유효성 검사(닉네임 패턴 및 중복 확인)
        String nicknamePattern = "^[a-zA-Z가-힣0-9]{2,12}$";
        validatePatternMatch(nicknamePattern, nickname, INVALID_NICKNAME);
        validateNicknameNotExist(nickname);
        return responseService.successWithNoContent(SUCCESS);
    }

    @Override
    public CommonResponse<?> validateEmail(String email) {
        // 1. 유효성 검사(이메일 중복 확인)
        validateEmailNotExist(email);
        return responseService.successWithNoContent(SUCCESS);
    }

    @Override
    public CommonResponse<?> validatePhone(String phone) {
        // 1. 유효성 검사(전화번호 형식 및 중복 확인)
        validPhoneFormat(phone);
        validatePhoneNotExist(phone);
        return responseService.successWithNoContent(SUCCESS);
    }

    @Override
    public CommonResponse<?> validatePassword(String password) {
        // 1. 유효성 검사(비밀번호 패턴 확인)
        String passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?!.*\\s).{8,16}$";
        validatePatternMatch(passwordPattern, password, INVALID_PASSWORD);

        return responseService.successWithNoContent(SUCCESS);
    }

    @Override
    public CommonResponse<?> validateConfirmPassword(String password, String confirmPassword) {
        // 1. 유효성 검사(비밀번호 패턴 및 일치 여부 확인)
        validatePassword(confirmPassword);
        validatePasswordMatch(password, confirmPassword);
        return responseService.successWithNoContent(SUCCESS);
    }

    /** 닉네임 중복 확인 */
    private void validateNicknameNotExist(String nickname) {
        memberRepository.findByNickname(nickname).ifPresent(member -> {
            throw new MemberException(ALREADY_EXISTS_NICKNAME);
        });
    }

    /** 이메일 중복 확인 */
    private void validateEmailNotExist(String email) {
        memberRepository.findByEmail(email).ifPresent(member -> {
            throw new MemberException(ALREADY_EXISTS_EMAIL);
        });
    }

    /** 전화번호 중복 확인 */
    private void validatePhoneNotExist(String phone) {
        memberRepository.findByPhone(phone).ifPresent(member -> {
            throw new MemberException(ALREADY_EXISTS_PHONE);
        });
    }

    /** 전화번호 형식 확인 */
    public void validPhoneFormat(String phone) {
        String phonePattern = "^\\d{2,3}-\\d{3,4}-\\d{4}$";
        validatePatternMatch(phonePattern, phone, INVALID_PHONE_FORMAT);
    }

    /** 비밀번호 일치 여부 확인 */
    public void validatePasswordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new MemberException(MISMATCH_PASSWORD);
        }
    }

    /** 인증 이메일 전송 */
    private void sendAuthConfirmEmail(Member member, EmailAuth emailAuth) {
        String title = "인증번호 안내";
        String text =
                "<p>" + member.getNickname() + "님 안녕하세요!<p>" +
                        "<p>아래 메일인증 버튼을 클릭하여 회원가입을 완료해 주세요.</p>"
                        + "<div><a target='_blank' href='http://localhost:8080/members/authConfirm?id=" + emailAuth.getId() + "&emailAuthToken=" + emailAuth.getEmailAuthToken() + "'> 메일 인증 </a><</div>";

        mailComponents.sendMail(member.getEmail(), title, text);
    }

    /** 이메일 인증 정보 조회 */
    private EmailAuth getEmailAuthById(Long id) {
        return emailAuthRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("이메일 인증 정보를 찾을 수 없습니다."));
    }

    /** 이메일 인증 토큰 일치 여부 확인 */
    private static void validateEmailAuthTokenMatch(EmailAuth emailAuth, String emailAuthToken) {
        if (!emailAuth.getEmailAuthToken().equals(emailAuthToken)) {
            throw new RuntimeException("이메일 인증 토큰이 유효하지 않습니다.");
        }
    }

    /** 입력받은 파라미터가 패턴과 일치 여부 확인 */
    private void validatePatternMatch(String pattern, String parameter, ErrorCode errorCode) {
        if (!parameter.matches(pattern)) {
            throw new MemberException(errorCode);
        }
    }
}
