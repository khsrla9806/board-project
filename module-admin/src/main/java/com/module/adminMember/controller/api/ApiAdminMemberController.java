package com.module.adminMember.controller.api;

import com.module.adminMember.dto.AdminMemberDto;
import com.module.adminMember.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ApiAdminMemberController {

    private final AdminMemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<AdminMemberDto>> memberSorting(
            @RequestParam(defaultValue = "") String sortBy,
            @RequestParam String sortOrder) {

        List<AdminMemberDto> response = memberService.findAllMemberWithBoardCountAndReplyCountOrderBy(sortBy, sortOrder);

        return ResponseEntity.ok(response);
    }

}
