package com.board.reply.repository;

import com.board.reply.domain.Reply;
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

    private final ReplyRepository replyRepository;

    public ReplyRepositoryTest(@Autowired ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @DisplayName("insert 테스트")
    @Test
    public void insert() {
        //given
        long totalCount = replyRepository.count();
        final Long boardId = 1L;
        final Long memberId = 1L;

        Reply reply = Reply.builder()
                .id(1L)
                .content("댓글 테스트입니다.")
                .boardId(boardId)
                .memberId(memberId)
                .build();

        //when
        replyRepository.save(reply);

        //then
        assertThat(replyRepository.count()).isEqualTo(totalCount + 1);

    }

    @DisplayName("delete 테스트")
    @Test
    public void delete() {
        //given
        long totalCount = replyRepository.count();
        final Long boardId = 1L;
        final Long memberId = 1L;

        Reply reply = Reply.builder()
                .id(1L)
                .content("댓글 테스트입니다.")
                .boardId(boardId)
                .memberId(memberId)
                .build();

        replyRepository.save(reply);

        //when
        replyRepository.deleteById(reply.getId());
        
        //then
        assertThat(replyRepository.findAll()).doesNotContain(reply);
        
    }
}