package com.module.member.controller;

import com.module.member.dto.MemberUpdate;
import com.module.member.dto.PasswordUpdate;
import com.module.member.service.MemberService;
import com.module.response.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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

    @PostMapping("/validateCurrentPassword")
    public ResponseEntity<?> validateCurrentPassword(String password, Principal principal) {
        String email = principal.getName();
        CommonResponse<?> result = memberService.validateCurrentPassword(email, password);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/updateMember")
    public ResponseEntity<?> updateMember(Principal principal, @RequestBody MemberUpdate.Request request) {
        String email = principal.getName();
        CommonResponse<?> result = memberService.updateMember(email, request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/updatePassword")
    private ResponseEntity<?> updatePassword(Principal principal, @RequestBody PasswordUpdate.Request request) {
        String email = principal.getName();
        CommonResponse<?> result = memberService.updatePassword(email, request);
        return ResponseEntity.ok(result);
    }
}
