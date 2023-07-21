package com.module.adminMember.service;

import com.module.adminMember.dto.AdminMemberDto;
import com.module.member.type.MemberRole;
import com.module.response.dto.CommonResponse;

import java.util.List;

public interface AdminMemberService {

    List<AdminMemberDto> findAllMemberWithBoardCountAndReplyCount();

    List<AdminMemberDto> findAllMemberWithBoardCountAndReplyCountOrderBy(String sortBy, String sortOrder);

    CommonResponse<?> activeMember(Long memberId);

    CommonResponse<?> blockMember(Long memberId);

    void updateMemberRole(Long memberId, MemberRole memberRole);
}
