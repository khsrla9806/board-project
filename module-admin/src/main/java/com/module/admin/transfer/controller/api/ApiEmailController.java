package com.module.admin.transfer.controller.api;

import com.module.admin.transfer.service.EmailService;
import com.module.response.dto.CommonResponse;
import com.module.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin/email")
@RequiredArgsConstructor
public class ApiEmailController {

    private final EmailService emailService;
    private final ResponseService responseService;

    @PostMapping("/send/{receiverId}")
    @ResponseBody
    public ResponseEntity<?> sendEmail(
            Principal principal
            , @PathVariable("receiverId") String receiverId
            , @RequestParam("title") String title
            , @RequestParam("content") String content
    ) {

        String senderEmail = principal.getName();
        CommonResponse<?> result = emailService.sendAndSaveEmail(title, content, senderEmail, Long.valueOf(receiverId));

        return ResponseEntity.ok(result);
    }
}
