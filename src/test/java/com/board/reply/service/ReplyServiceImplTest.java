package com.board.reply.service;

import com.board.board.domain.Board;
import com.board.board.repository.BoardRepository;
import com.board.board.type.Category;
import com.board.board.type.Status;
import com.board.reply.domain.Reply;
import com.board.reply.dto.ReplyDto;
import com.board.reply.repository.ReplyRepository;
import com.board.reply.service.impl.ReplyServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("ReplyServiceImpl 테스트")
@ExtendWith(MockitoExtension.class)
public class ReplyServiceImplTest {

    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private ReplyServiceImpl replyService;

    @DisplayName("게시글 id (boardId) 가 주어지면 게시글에 포함된 댓글을 출력할 수 있는지 테스트")
    @Test
    public void findRepliesTest() {
        // given
        Board board = createBoard();
        Reply parentReply = createReply(1L, board);
        Reply childReply = createReply(2L, board);
        childReply.setParent(parentReply);

        given(boardRepository.findById(board.getId())).willReturn(Optional.of(board));
        given(replyRepository.findByBoardIdAndParentIsNull(board.getId())).willReturn(List.of(
                parentReply,
                childReply
        ));

        // when
        ReplyDto.BoardWithReplyDto replies = replyService.findBoardWithReplies(board.getId());

        // then
        List<ReplyDto.ReplyResponseDto> replyResponseDtos = replies.getReplyResponseDtos();
        assertThat(replyResponseDtos)
                .extracting("id", "content", "parentId", "boardId", "memberId")
                .contains(
                        tuple(1L, "1의 댓글 테스트입니다.", null, 1L, 1L),
                        tuple(2L, "2의 댓글 테스트입니다.", 1L, 1L, 1L)
                );
        then(replyRepository).should().findByBoardIdAndParentIsNull(board.getId());
    }

    @DisplayName("댓글에 대한 정보를 입력하면 댓글을 등록하는지 테스트")
    @Test
    public void addReplyTest() {
        // given
        Board board = createBoard();
        ReplyDto.ReplyRequestDto replyRequestDto = createReplyRequestDto();
        given(boardRepository.findById(board.getId())).willReturn(Optional.of(board));
        given(replyRepository.save(any(Reply.class))).willReturn(null);

        // when
        replyService.addReply(replyRequestDto);

        // then
        then(boardRepository).should().findById(board.getId());
        then(replyRepository).should().save(any(Reply.class));
    }

    @DisplayName("댓글 등록시 게시글이 존재하지 않는다면 에러 발생")
    @Test
    public void addReplyThrowExceptionTest() {
        // given
        ReplyDto.ReplyRequestDto replyRequestDto = createReplyRequestDto();
        given(boardRepository.findById(replyRequestDto.getBoardId())).willReturn(Optional.empty());

        // when & then
        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> {
            replyService.addReply(replyRequestDto);
        });
        assertEquals("게시글을 찾을 수 없습니다 : " + replyRequestDto.getBoardId(),
                entityNotFoundException.getMessage());
    }

    @DisplayName("대댓글 등록시 부모 댓글 (parentId)가 존재하지 않는다면 에러 발생")
    @Test
    public void addSubReplyThrowExceptionTest() {
        // given
        Board board = createBoard();
        ReplyDto.ReplyRequestDto replyRequestDto = createReplyRequestDto();
        replyRequestDto.setParentId(1L);
        given(boardRepository.findById(replyRequestDto.getBoardId())).willReturn(Optional.of(board));
        given(replyRepository.findById(replyRequestDto.getParentId())).willReturn(Optional.empty());

        // when & then
        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> {
            replyService.addReply(replyRequestDto);
        });
        assertEquals("댓글을 찾을 수 없습니다. : " + replyRequestDto.getParentId(),
                entityNotFoundException.getMessage());
    }

    @DisplayName("댓글에 id 를 전달하면 댓글을 삭제하는지 테스트")
    @Test
    public void deleteReplyTest() {
        // given
        final Long replyId = 1L;
        willDoNothing().given(replyRepository).deleteById(replyId);

        // when
        replyService.removeReply(replyId);

        // then
        then(replyRepository).should().deleteById(replyId);
    }

    private ReplyDto.ReplyRequestDto createReplyRequestDto() {
        return ReplyDto.ReplyRequestDto.builder()
                .content("댓글 테스트입니다.")
                .boardId(1L)
                .parentId(null)
                .memberId(1L)
                .build();
    }

    private Reply createReply(Long replyId, Board board) {
        ReplyDto.ReplyRequestDto replyRequestDto = ReplyDto.ReplyRequestDto.builder()
                .content(replyId + "의 댓글 테스트입니다.")
                .boardId(1L)
                .memberId(1L)
                .build();

        Reply reply = replyRequestDto.toEntity(board, null,1L);
        ReflectionTestUtils.setField(reply, "id", replyId);

        return reply;
    }

    private Board createBoard() {
        Board board = new Board();
        board.setId(1L);
        board.setTitle("제목 테스트입니다.");
        board.setContent("본문내용 테스트입니다.");
        board.setCategory(Category.COMMON);
        board.setStatus(Status.ACTIVE);
        board.setMember_id(1L);

        return board;
    }

}