package com.module.admin.board.service.impl;

import com.module.admin.board.dto.AdminBoardDto;
import com.module.admin.board.repository.AdminBoardRepository;
import com.module.admin.board.service.AdminBoardService;
import com.module.board.domain.Board;
import com.module.board.type.Status;
import com.module.response.dto.CommonResponse;
import com.module.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.module.response.type.SuccessCode.SUCCESS;

@Service
@RequiredArgsConstructor
public class AdminBoardServiceImpl implements AdminBoardService {

    private final ResponseService responseService;
    private final AdminBoardRepository boardRepository;

    @Override
    public CommonResponse<?> findBoardsByMemberIdOrderByCreatedAtDesc(Long memberId) {
        List<Board> boards = boardRepository.findBoardsByMemberIdOrderByCreatedAtDesc(memberId);
        return responseService.success(
                boards.stream()
                        .map(AdminBoardDto::fromEntity)
                        .collect(Collectors.toList()), SUCCESS);
    }

    @Override
    public CommonResponse<?> updateBoardStatus(Long board, String status) {
        if (Objects.equals(status, Status.ACTIVE.toString())) {
            boardRepository.updateBoardStatusToActive(board);
        } else if (Objects.equals(status, Status.FORBIDDEN.toString())) {
            boardRepository.updateBoardStatusToForbidden(board);
        } else if (Objects.equals(status, Status.DELETED.toString())) {
            boardRepository.updateBoardStatusToDeleted(board);
        }

        return responseService.successWithNoContent(SUCCESS);
    }

}
