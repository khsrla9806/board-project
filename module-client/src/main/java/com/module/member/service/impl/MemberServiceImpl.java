package com.module.member.service.impl;

import com.module.global.components.MailComponents;
import com.module.global.exception.MemberException;
import com.module.member.domain.EmailAuth;
import com.module.member.domain.Member;
import com.module.member.dto.MemberDto;
import com.module.member.dto.MemberRegistration;
import com.module.member.dto.MemberUpdate;
import com.module.member.dto.PasswordUpdate;
import com.module.member.repository.EmailAuthRepository;
import com.module.member.repository.MemberRepository;
import com.module.member.service.MemberService;
import com.module.response.dto.CommonResponse;
import com.module.response.service.ResponseService;
import com.module.response.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.module.member.type.MemberRole.COMMON;
import static com.module.member.type.MemberStatus.ACTIVE;
import static com.module.member.type.MemberStatus.INACTIVE;
import static com.module.response.type.ErrorCode.*;
import static com.module.response.type.SuccessCode.SUCCESS;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final EmailAuthRepository emailAuthRepository;
    private final MemberRepository memberRepository;
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

        try {
            // 5. 회원가입 요청시 5초 대기
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

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
    public CommonResponse<?> getMemberDetailsByEmail(String email) {
        // 1. 유효성 검사
        Member member = getMemberByEmail(email);

        return responseService.success(MemberDto.fromEntity(member), SUCCESS);
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
        String phonePattern = "^\\d{2,3}-\\d{3,4}-\\d{4}$";
        validatePatternMatch(phonePattern, phone, INVALID_PHONE_FORMAT);
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

    @Override
    public CommonResponse<?> validateCurrentPassword(String email, String password) {
        // 1. 유효성 검사(이메일로 회원여부 확인 및 비밀번호 확인)
        Member member = getMemberByEmail(email);
        if (!validateCurrentPasswordMatch(password, member)) {
            return responseService.failure(MISMATCH_PASSWORD);
        }

        return responseService.successWithNoContent(SUCCESS);
    }

    @Override
    @Transactional
    public CommonResponse<?> updateMember(String email, MemberUpdate.Request request) {
        // 1. 유효성 검사(이메일로 회원여부 확인 및 닉네임, 전화번호 확인)
        Member member = getMemberByEmail(email);
        validateUpdateMember(request, member);

        // 2. 회원정보 업데이트
        member.updateMember(request);

        return responseService.success(MemberUpdate.Response.fromEntity(member), SUCCESS);
    }

    @Override
    @Transactional
    public CommonResponse<?> updatePassword(String email, PasswordUpdate.Request request) {
        String currentPassword = request.getCurrentPassword();
        String newPassword = request.getNewPassword();
        String newConfirmPassword = request.getNewConfirmPassword();

        // 1. 유효성 검사(이메일로 회원여부 확인)
        Member member = getMemberByEmail(email);

        // 2. 유효성 검사(비밀번호)
        if (!validateCurrentPasswordMatch(currentPassword, member)) {
            return responseService.failure(MISMATCH_PASSWORD);
        }

        String passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?!.*\\s).{8,16}$";
        validatePatternMatch(passwordPattern, newPassword, INVALID_PASSWORD);

        validatePassword(newConfirmPassword);
        validatePasswordMatch(newPassword, newConfirmPassword);

        // 3. 비밀번호 업데이트
        member.updatePassword(passwordEncoder.encode(newPassword));

        return responseService.success(MemberDto.fromEntity(member), SUCCESS);
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

    /** 현재 비밀번호 일치 여부 확인 */
    private boolean validateCurrentPasswordMatch(String password, Member member) {
        return passwordEncoder.matches(password, member.getPassword());
    }

    /** 비밀번호 일치 여부 확인 */
    private void validatePasswordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new MemberException(MISMATCH_PASSWORD);
        }
    }

    /** 인증 이메일 전송 */
    private void sendAuthConfirmEmail(Member member, EmailAuth emailAuth) {
        String title = "[FCBE5-4] 인증번호 안내";
        StringBuffer text = new StringBuffer();
        text.append("<!DOCTYPE html>");
        text.append("<html>");
        text.append("<head>");
        text.append("</head>");
        text.append("<body>");
        text.append(
                " <div" +
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                        "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">FCBE5-4</span><br />" +
                        "		<span style=\"color: #02b875\">메일인증</span> 안내입니다." +
                        "	</h1>\n" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        member.getNickname() +
                        "		님 안녕하세요.<br />" +
                        "		아래 <b style=\"color: #02b875\">'메일 인증'</b> 버튼을 클릭하여 회원가입을 완료해 주세요.<br />" +
                        "		감사합니다." +
                        "	</p>" +
                        "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\"" +
                        "	href=\"http://localhost:8080/members/auth/confirm?id=" + emailAuth.getId() + "&emailAuthToken=" + emailAuth.getEmailAuthToken() + "\" target=\"_blank\">" +
                        "		<p" +
                        "			style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;\">" +
                        "			메일 인증</p>" +
                        "	</a>" +
                        "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>" +
                        " </div>"
        );
        text.append("</body>");
        text.append("</html>");

        mailComponents.sendMail(member.getEmail(), title, text.toString());
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

    /** 이메일로 회원 정보 확인 */
    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(LOAD_USER_FAILED));
    }

    /** 회원정보 변경 유효성 검사 */
    private void validateUpdateMember(MemberUpdate.Request request, Member member) {
        if (!request.getNickname().equals(member.getNickname())) {
            validateNicknameNotExist(request.getNickname());
        }
        if (!request.getPhone().equals(member.getPhone())) {
            validatePhoneNotExist(request.getNickname());
        }
    }
}
