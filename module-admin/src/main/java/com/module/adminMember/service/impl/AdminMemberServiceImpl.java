package com.module.adminMember.service.impl;

import com.module.adminMember.dto.AdminMemberDto;
import com.module.adminMember.repository.AdminMemberRepository;
import com.module.adminMember.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {

    private final AdminMemberRepository memberRepository;


    @Override
    public List<AdminMemberDto> findAllMemberWithBoardCountAndReplyCount() {
        return memberRepository.findAllMemberWithBoardCountAndReplyCount();
    }

    @Override
    public List<AdminMemberDto> findAllMemberWithBoardCountAndReplyCountOrderBy(String sortBy, String sortOrder) {
        return memberRepository.findAllMemberWithBoardCountAndReplyCountOrderBy(sortBy, sortOrder);
    }
}
