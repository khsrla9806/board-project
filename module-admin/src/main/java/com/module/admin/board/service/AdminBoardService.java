package com.module.admin.board.service;

import com.module.response.dto.CommonResponse;

public interface AdminBoardService {

    CommonResponse<?> findBoardsByMemberIdOrderByCreatedAtDesc(Long memberId);

    CommonResponse<?> updateBoardStatus(Long boardId, String status);
}
