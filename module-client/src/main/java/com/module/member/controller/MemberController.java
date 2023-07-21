package com.module.member.controller;

import com.module.member.dto.MemberRegistration;
import com.module.member.service.MemberService;
import com.module.response.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String register() {
        return "members/register";
    }

    @PostMapping("/register")
    public String register(@Valid MemberRegistration request) {
        memberService.register(request);
        return "members/register_complete";
    }

    @GetMapping("/auth/confirm")
    public String emailAuth(Model model, HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        String emailAuthToken = request.getParameter("emailAuthToken");

        CommonResponse<?> response = memberService.authConfirm(id, emailAuthToken);
        model.addAttribute("response", response);

        return "members/auth-confirm";
    }

    @RequestMapping("/login")
    public String login() {
        return "members/login";
    }

    @GetMapping("/myPage")
    public String myPage(Model model, Principal principal) {
        if (validatePrincipal(principal)) {
            return "redirect:/members/login";
        }

        String email = principal.getName();
        CommonResponse<?> response = memberService.getMemberDetailsByEmail(email);
        model.addAttribute("response", response);

        return "members/myPage";
    }

    @RequestMapping("/password-confirm")
    public String passwordConfirm() {
        return "members/password-confirm";
    }

    @RequestMapping("/update-member")
    public String updateMember(Model model, Principal principal) {
        if (validatePrincipal(principal)) {
            return "redirect:/members/login";
        }

        String email = principal.getName();
        CommonResponse<?> response = memberService.getMemberDetailsByEmail(email);
        model.addAttribute("response", response);

        return "members/update-member";
    }

    @RequestMapping("/update-password")
    public String updatePassword(Model model, Principal principal) {
        if (validatePrincipal(principal)) {
            return "redirect:/members/login";
        }

        String email = principal.getName();
        CommonResponse<?> response = memberService.getMemberDetailsByEmail(email);
        model.addAttribute("response", response);

        return "members/update-password";
    }

    private boolean validatePrincipal(Principal principal) {
        return principal == null;
    }
}
