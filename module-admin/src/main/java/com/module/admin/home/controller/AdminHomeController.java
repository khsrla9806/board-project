package com.module.admin.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class AdminHomeController {

    @RequestMapping("/")
    public String home(Principal principal) {
        if (validatePrincipal(principal)) {
            return "redirect:/members/login";
        }

        return "redirect:/admin/members";
    }

    /** 로그인된 회원 검증 */
    private boolean validatePrincipal(Principal principal) {
        return principal == null;
    }
}
