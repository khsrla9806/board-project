package com.module.adminMember.controller.api;

import com.module.adminMember.dto.AdminMemberDto;
import com.module.adminMember.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/active/{memberId}")
    public ResponseEntity<?> activeMember(@PathVariable String memberId) {
        return ResponseEntity.ok(memberService.activeMember(Long.valueOf(memberId)));
    }

    @PostMapping("/block/{memberId}")
    public ResponseEntity<?> blockMember(@PathVariable String memberId) {
        return ResponseEntity.ok(memberService.blockMember(Long.valueOf(memberId)));
    }

}
