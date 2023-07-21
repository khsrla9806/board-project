package com.module.adminMember.repository;

import com.module.adminMember.dto.AdminMemberDto;
import com.module.member.type.MemberStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMemberRepository {

    List<AdminMemberDto> findAllMemberWithBoardCountAndReplyCount();

    List<AdminMemberDto> findAllMemberWithBoardCountAndReplyCountOrderBy(
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder
    );

    void updateMemberStatus(@Param("memberId") Long memberId, @Param("memberStatus") MemberStatus memberStatus);

}
