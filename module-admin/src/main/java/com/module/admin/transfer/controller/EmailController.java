package com.module.admin.transfer.controller;

import com.module.admin.transfer.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/write/{receiverId}")
    public String showEmailForm(Model model, @PathVariable String receiverId) {
        model.addAttribute("receiverId", receiverId);
        return "admin/create-email-form";
    }

    @PostMapping("/send/{receiverId}")
    public String sendEmail(
            Principal principal
            , @PathVariable("receiverId") String receiverId
            , @RequestParam("title") String title
            , @RequestParam("content") String content
    ) {
        String senderEmail = principal.getName();
        emailService.sendAndSaveEmail(title, content, senderEmail, Long.valueOf(receiverId));
        return "redirect:/admin/members";
    }

}
