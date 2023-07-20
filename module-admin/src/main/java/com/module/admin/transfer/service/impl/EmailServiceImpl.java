package com.module.admin.transfer.service.impl;

import com.module.admin.transfer.dto.EmailTransfer;
import com.module.admin.transfer.repository.EmailRepository;
import com.module.admin.transfer.service.EmailService;
import com.module.global.components.MailComponents;
import com.module.global.exception.MemberException;
import com.module.member.domain.Member;
import com.module.member.repository.MemberRepository;
import com.module.response.dto.CommonResponse;
import com.module.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.module.response.type.ErrorCode.LOAD_USER_FAILED;
import static com.module.response.type.SuccessCode.SUCCESS;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final ResponseService responseService;

    private final EmailRepository emailRepository;
    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    @Override
    @Transactional
    public CommonResponse<?> sendAndSaveEmail(String title, String content, String senderEmail, Long receiverId) {
        // 1. 유효성 확인(작성자, 수신자 유효성 확인)
        Member sender = getMemberByEmail(senderEmail);
        Member receiver = getMemberById(receiverId);

        // 2. 이메일 전송 및 정보 저장
        EmailTransfer emailTransfer = EmailTransfer.builder()
                .title(title)
                .content(content)
                .sender(sender)
                .receiver(receiver)
                .build();

//        emailRepository.save(emailTransfer);
        sendEmail(emailTransfer, receiver);

        return responseService.successWithNoContent(SUCCESS);
    }

    private void sendEmail(EmailTransfer emailTransfer, Member receiver) {
        // 1. 제목 설정
        String title = emailTransfer.getTitle();
        
        // 2. 본문 설정
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
                        "		<span style=\"color: #02b875\">관리자 메일</span> 안내입니다." +
                        "	</h1>\n" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        receiver.getNickname() +
                        "		님 안녕하세요. <br /> <br />" +
                        emailTransfer.getContent() +
                        "<br /> <br />" +
                        "		감사합니다." +
                        "	</p>" +

                        "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>" +
                        " </div>"
        );
        text.append("</body>");
        text.append("</html>");

        // 3. 메일 전송
        mailComponents.sendMail(receiver.getEmail(), title, text.toString());
    }

    /** 이메일로 회원 정보 확인 */
    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(LOAD_USER_FAILED));
    }

    /** 고유 아이디로 회원 정보 확인 */
    private Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(LOAD_USER_FAILED));
    }
}
