package com.module.adminMember.repository;

import com.module.adminMember.dto.AdminMemberDto;
import com.module.member.domain.Member;
import com.module.member.type.MemberRole;
import com.module.member.type.MemberStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminMemberRepository {

    List<AdminMemberDto> findAllMemberWithBoardCountAndReplyCount();

    List<AdminMemberDto> findAllMemberWithBoardCountAndReplyCountOrderBy(
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder
    );

    void updateMemberStatus(@Param("memberId") Long memberId, @Param("memberStatus") MemberStatus memberStatus);

    Optional<Member> findMemberById(@Param("memberId") Long memberId);

    void updateMemberById(@Param("memberId") Long memberId, @Param("memberRole") MemberRole memberRole);

}
