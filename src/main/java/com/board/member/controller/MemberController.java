package com.board.member.controller;

import com.board.member.dto.MemberRegistration;
import com.board.member.service.MemberService;
import com.board.response.dto.CommonResult;
import com.board.response.dto.SingleResult;
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
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/register")
    public String register(@Valid MemberRegistration request) {
        SingleResult<Long> result = memberService.register(request);
        return "member/register";
    }

    @GetMapping("/authConfirm")
    public String emailAuth(Model model, HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        String emailAuthToken = request.getParameter("emailAuthToken");

        CommonResult commonResult = memberService.authConfirm(id, emailAuthToken);
        model.addAttribute("result", commonResult);

        return "member/authConfirm";
    }
}
