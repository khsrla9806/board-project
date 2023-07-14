package com.board.reply.repository;

import com.board.board.domain.Board;
import com.board.board.repository.BoardRepository;
import com.board.board.type.Category;
import com.board.board.type.Status;
import com.board.reply.domain.Reply;
import com.board.reply.dto.ReplyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReplyRepository 연결 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReplyRepositoryTest {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public ReplyRepositoryTest(
            @Autowired BoardRepository boardRepository,
            @Autowired ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }

    @BeforeEach
    public void setUp() {
        createBoardAndReply();
    }

    @DisplayName("insert 테스트")
    @Test
    public void insert() {
        //given
        final Long userId = 1L;
        long boardTotalCount = boardRepository.count();
        long replyTotalCount = replyRepository.count();
        Board board = new Board();
        board.setTitle("제목 테스트입니다.");
        board.setContent("본문내용 테스트입니다.");
        board.setCategory(Category.COMMON);
        board.setStatus(Status.ACTIVE);
        board.setMember_id(1L);

        boardRepository.save(board);

        ReplyDto.ReplyRequestDto replyRequestDto = ReplyDto.ReplyRequestDto.builder()
                .boardId(board.getId())
                .memberId(board.getMember_id())
                .content("댓글 테스트입니다.")
                .build();

        Reply reply = replyRequestDto.toEntity(board, null, userId);

        //when
        replyRepository.save(reply);

        //then
        assertThat(boardRepository.count()).isEqualTo(boardTotalCount + 1);
        assertThat(replyRepository.count()).isEqualTo(replyTotalCount + 1);

    }

    @DisplayName("delete 테스트")
    @Test
    public void delete() {
        //given
        final Long userId = 1L;
        Board board = new Board();
        board.setTitle("제목 테스트입니다.");
        board.setContent("본문내용 테스트입니다.");
        board.setCategory(Category.COMMON);
        board.setStatus(Status.ACTIVE);
        board.setMember_id(1L);

        boardRepository.save(board);

        ReplyDto.ReplyRequestDto replyRequestDto = ReplyDto.ReplyRequestDto.builder()
                .boardId(board.getId())
                .memberId(board.getMember_id())
                .content("댓글 테스트입니다.")
                .build();

        Reply reply = replyRequestDto.toEntity(board, null, userId);

        replyRepository.save(reply);

        //when
        replyRepository.deleteById(reply.getId());
        boardRepository.deleteById(board.getId());

        //then
        assertThat(boardRepository.findAll()).doesNotContain(board);
        assertThat(replyRepository.findAll()).doesNotContain(reply);

    }

    private void createBoardAndReply() {
        for (long i = 1; i <= 3; i++) {
            Board board = new Board();
            board.setTitle(i + "의 제목 입니다.");
            board.setContent(i + "의 본문내용 입니다.");
            board.setCategory(Category.COMMON);
            board.setStatus(Status.ACTIVE);
            board.setMember_id(1L);

            boardRepository.save(board);

            for (long j = 1; j <= 3; j++) {
                ReplyDto.ReplyRequestDto replyRequestDto = ReplyDto.ReplyRequestDto.builder()
                        .content(j + "번째 댓글입니다.")
                        .boardId(j)
                        .memberId(1L)
                        .build();

                Reply reply = replyRequestDto.toEntity(board, null, 1L);
                replyRepository.save(reply);
            }
        }
    }

}