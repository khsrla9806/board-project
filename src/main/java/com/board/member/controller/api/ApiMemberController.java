package com.board.member.controller.api;

import com.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class ApiMemberController {

    private final MemberService memberService;

    @GetMapping("/validateEmailNotExist")
    public ResponseEntity<?> validateEmailNotExist(@RequestParam String email) {

        return null;
    }

}
