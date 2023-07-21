package com.module.adminMember.service.impl;

import com.module.adminMember.dto.AdminMemberDto;
import com.module.adminMember.repository.AdminMemberRepository;
import com.module.adminMember.service.AdminMemberService;
import com.module.member.domain.Member;
import com.module.member.type.MemberRole;
import com.module.member.type.MemberStatus;
import com.module.response.dto.CommonResponse;
import com.module.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.module.response.type.SuccessCode.SUCCESS;

@Service
@RequiredArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {

    private final AdminMemberRepository memberRepository;
    private final ResponseService responseService;

    @Override
    public List<AdminMemberDto> findAllMemberWithBoardCountAndReplyCount() {
        return memberRepository.findAllMemberWithBoardCountAndReplyCount();
    }

    @Override
    public List<AdminMemberDto> findAllMemberWithBoardCountAndReplyCountOrderBy(String sortBy, String sortOrder) {
        return memberRepository.findAllMemberWithBoardCountAndReplyCountOrderBy(sortBy, sortOrder);
    }

    @Override
    public CommonResponse<?> activeMember(Long memberId) {
        memberRepository.updateMemberStatus(memberId, MemberStatus.ACTIVE);
        return responseService.successWithNoContent(SUCCESS);
    }

    @Override
    public CommonResponse<?> blockMember(Long memberId) {
        memberRepository.updateMemberStatus(memberId, MemberStatus.BLOCKED);
        return responseService.successWithNoContent(SUCCESS);
    }

    @Transactional
    @Override
    public void updateMemberRole(Long memberId, MemberRole memberRole) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        memberRepository.updateMemberById(member.getId(), memberRole);
    }
}
