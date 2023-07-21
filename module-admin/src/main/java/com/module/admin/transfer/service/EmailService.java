package com.module.admin.transfer.service;

import com.module.response.dto.CommonResponse;

public interface EmailService {

    // 이메일 전송 및 정보 저장
    CommonResponse<?> sendAndSaveEmail(String title, String content, String senderEmail, Long receiverId);

}
