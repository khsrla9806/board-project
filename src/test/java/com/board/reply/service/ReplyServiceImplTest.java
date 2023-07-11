package com.board.reply.service;

import com.board.reply.domain.Reply;
import com.board.reply.dto.ReplyDto;
import com.board.reply.repository.ReplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("ReplyServiceImpl 테스트")
@ExtendWith(MockitoExtension.class)
public class ReplyServiceImplTest {

    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private ReplyServiceImpl replyService;

    @DisplayName("댓글에 대한 정보를 입력하면 댓글을 등록하는지 테스트")
    @Test
    public void addReplyTest() {
        // Given
        final Long boardId = 1L;
        final Long memberId = 1L;
        ReplyDto.ReplyRequestDto replyRequestDto = ReplyDto.ReplyRequestDto.builder()
                .id(1L)
                .content("댓글 테스트입니다.")
                .boardId(boardId)
                .memberId(memberId)
                .build();

        Reply reply = replyRequestDto.toEntity();

        // When
        replyService.addReply(replyRequestDto);

        // Then
        verify(replyRepository, times(1)).save(reply);
    }

    @DisplayName("댓글에 id 를 전달하면 댓글을 삭제하는지 테스트")
    @Test
    public void deleteReplyTest() {
        // Given
        final Long replyId = 1L;
        final Long boardId = 1L;
        final Long memberId = 1L;
        ReplyDto.ReplyRequestDto replyRequestDto = ReplyDto.ReplyRequestDto.builder()
                .id(1L)
                .content("댓글 테스트입니다.")
                .boardId(boardId)
                .memberId(memberId)
                .build();

        replyService.addReply(replyRequestDto);

        // When
        replyService.removeReply(replyRequestDto.getId());

        // Then
        verify(replyRepository, times(1)).deleteById(replyId);
    }
}