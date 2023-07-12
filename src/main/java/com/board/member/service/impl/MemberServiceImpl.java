package com.board.member.service.impl;

import com.board.components.MailComponents;
import com.board.exception.MemberException;
import com.board.member.domain.EmailAuth;
import com.board.member.domain.Member;
import com.board.member.dto.MemberRegistration;
import com.board.member.repository.EmailAuthRepository;
import com.board.member.repository.MemberRepository;
import com.board.member.service.MemberService;
import com.board.response.dto.CommonResult;
import com.board.response.dto.SingleResult;
import com.board.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.board.member.type.MemberRole.COMMON;
import static com.board.member.type.MemberStatus.ACTIVE;
import static com.board.member.type.MemberStatus.INACTIVE;
import static com.board.response.type.ErrorCode.*;

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
    public SingleResult<Long> register(MemberRegistration request) {
        // 1. 유효성 검사(이메일, 전화번호, 닉네임 중복 확인)
        validateEmailNotExist(request.getEmail());
        validatePhoneNotExist(request.getPhone());
        validateNicknameNotExist(request.getNickname());

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

        return responseService.getSingleResult(member.getId());
    }

    @Override
    public CommonResult authConfirm(Long id, String emailAuthToken) {
        // 1. 유효성 검사(인증 정보, 토큰 일치 여부 확인)
        EmailAuth emailAuth = getEmailAuthById(id);
        validateEmailAuthTokenMatch(emailAuth, emailAuthToken);
        
        // 2. 이메일 인증 완료
        emailAuthRepository.save(emailAuth.confirmAuth());
        
        // 3. 계정 활성화
        Member member = emailAuth.getMember();
        member.updateMemberStatus(ACTIVE);
        memberRepository.save(member);

        return responseService.getSuccessResult();
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

    /** 닉네임 중복 확인 */
    private void validateNicknameNotExist(String nickname) {
        memberRepository.findByNickname(nickname).ifPresent(member -> {
            throw new MemberException(ALREADY_EXISTS_NICKNAME);
        });
    }

    /** 비밀번호 일치 여부 확인 */
    public void validatePasswordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new MemberException(INVALID_PASSWORD);
        }
    }

    /** 인증 이메일 전송 */
    private void sendAuthConfirmEmail(Member member, EmailAuth emailAuth) {
        String title = "인증번호 안내";
        String text =
                "<p>" + member.getNickname() + "님 안녕하세요!<p>" +
                        "<p>아래 메일인증 버튼을 클릭하여 회원가입을 완료해 주세요.</p>"
                        + "<div><a target='_blank' href='http://localhost:8080/member/authConfirm?id=" + emailAuth.getId() + "&emailAuthToken=" + emailAuth.getEmailAuthToken() + "'> 메일 인증 </a><</div>";

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
}
