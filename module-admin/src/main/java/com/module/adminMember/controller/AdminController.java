package com.module.adminMember.controller;

import com.module.adminMember.dto.AdminMemberDto;
import com.module.adminMember.service.AdminMemberService;
import com.module.member.type.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminMemberService memberService;

    @GetMapping("/members")
    public String index(Model model) {

        List<AdminMemberDto> response = memberService.findAllMemberWithBoardCountAndReplyCount();
        model.addAttribute("response", response);

        return "/admin/members";
    }

    @GetMapping("/members-control")
    public String memberControl(Model model) {

        List<AdminMemberDto> response = memberService.findAllMemberWithBoardCountAndReplyCount();
        model.addAttribute("response", response);
        model.addAttribute("memberRoles", MemberRole.values());

        return "/admin/members-control";
    }

    @PostMapping("/member-role")
    public String updateRole(Long memberId, MemberRole memberRole) {
        memberService.updateMemberRole(memberId, memberRole);

        return "redirect:/admin/members-control";
    }

}
