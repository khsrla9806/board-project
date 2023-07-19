package com.module.member.repository;

import com.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일로 유저 검색
    Optional<Member> findByEmail(String email);

    // 전화번호로 유저 검색
    Optional<Member> findByPhone(String phone);

    // 닉네임으로 유저 검색
    Optional<Member> findByNickname(String nickname);

    // 게시물이 count 이상인 회원 목록 조회
    @Query("SELECT m FROM Member m JOIN m.boards b GROUP BY m HAVING COUNT(b) >= :count")
    List<Member> findMembersWithBoardCount(@Param("count") Long count);
}
