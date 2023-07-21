package com.module.admin.board.repository;

import com.module.board.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminBoardRepository {

    List<Board> findBoardsByMemberIdOrderByCreatedAtDesc(@Param("memberId") Long memberId);

    void updateBoardStatusToActive(@Param("boardId") Long boardId);

    void updateBoardStatusToForbidden(@Param("boardId") Long boardId);

    void updateBoardStatusToDeleted(@Param("boardId") Long boardId);
}
