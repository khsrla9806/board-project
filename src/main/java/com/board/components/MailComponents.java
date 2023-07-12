package com.board.components;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class MailComponents {
    
    private final JavaMailSender javaMailSender;
    
    /** 인증 메일 전송 */
    public void sendMail(String mail, String subject, String text) {
        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(text, true);
            }
        };
        
        try {
            javaMailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
