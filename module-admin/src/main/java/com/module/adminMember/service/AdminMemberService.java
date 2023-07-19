package com.module.adminMember.service;

import com.module.adminMember.dto.AdminMemberDto;

import java.util.List;

public interface AdminMemberService {

    List<AdminMemberDto> findAllMemberWithBoardCountAndReplyCount();

    List<AdminMemberDto> findAllMemberWithBoardCountAndReplyCountOrderBy(String sortBy, String sortOrder);

}
