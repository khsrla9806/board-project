package com.board.member.controller.api;

import com.board.member.service.MemberService;
import com.board.response.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/")
public class ApiMemberController {

    private final MemberService memberService;

    @PostMapping("/validateUsername")
    public ResponseEntity<?> validateUsername(String username) {
        CommonResponse<?> result = memberService.validateUsername(username);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/validateNickname")
    public ResponseEntity<?> validateNickName(String nickname) {
        CommonResponse<?> result = memberService.validateNickname(nickname);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/validateEmail")
    public ResponseEntity<?> validateEmail(String email) {
        CommonResponse<?> result = memberService.validateEmail(email);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/validatePhone")
    public ResponseEntity<?> validatePhone(String phone) {
        CommonResponse<?> result = memberService.validatePhone(phone);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/validatePassword")
    public ResponseEntity<?> validatePassword(String password) {
        CommonResponse<?> result = memberService.validatePassword(password);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/validateConfirmPassword")
    public ResponseEntity<?> validateConfirmPassword(String password, String confirmPassword) {
        CommonResponse<?> result = memberService.validateConfirmPassword(password, confirmPassword);
        return ResponseEntity.ok(result);
    }
}
