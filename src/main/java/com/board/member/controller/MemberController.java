package com.board.member.controller;

import com.board.member.dto.MemberRegistration;
import com.board.member.service.MemberService;
import com.board.response.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/***
 * 추후 API 컨트롤러 분리
 */
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
        CommonResponse<Long> result = memberService.register(request);
        return "members/register";
    }

    @RequestMapping("/login")
    public String login() {
        return "members/login";
    }

    @GetMapping("/authConfirm")
    public String emailAuth(Model model, HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        String emailAuthToken = request.getParameter("emailAuthToken");

        CommonResponse<?> result = memberService.authConfirm(id, emailAuthToken);
        model.addAttribute("result", result);

        return "members/authConfirm";
    }
}
