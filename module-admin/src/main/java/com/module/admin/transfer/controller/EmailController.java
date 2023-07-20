package com.module.admin.transfer.controller;

import com.module.admin.transfer.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/write/{receiverId}")
    public String showEmailForm(Model model, @PathVariable Long receiverId) {
        model.addAttribute("receiverId", receiverId);
        return "admin/create-email-form";
    }

}
