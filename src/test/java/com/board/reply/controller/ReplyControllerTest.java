package com.board.reply.controller;

import com.board.reply.dto.ReplyDto;
import com.board.reply.service.ReplyServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("ReplyController 테스트")
@WebMvcTest(
        controllers = ReplyController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class
)
class ReplyControllerTest {

    private final MockMvc mvc;

    @MockBean
    private ReplyServiceImpl replyServiceImpl;

    public ReplyControllerTest(@Autowired MockMvc mvc, @Autowired ReplyServiceImpl replyServiceImpl) {
        this.mvc = mvc;
        this.replyServiceImpl = replyServiceImpl;
    }

    @DisplayName("댓글 등록 - 정상 호출")
    @Test
    public void replyList() throws Exception {
        //given
        final Long boardId = 1L;
        ReplyDto.ReplyRequestDto.builder()
                .content("댓글 컨트롤러 테스트 작성 중")
                .memberId(1L)
                .boardId(boardId)
                .build();
        willDoNothing().given(replyServiceImpl).addReply(any(ReplyDto.ReplyRequestDto.class));

        //when & then
        mvc.perform(
                        post("/replies/new")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        then(replyServiceImpl).should().addReply(any(ReplyDto.ReplyRequestDto.class));
    }

    @DisplayName("댓글 삭제 - 정상 호출")
    @Test
    public void deleteReply() throws Exception {
        //given
        final Long replyId = 1L;
        willDoNothing().given(replyServiceImpl).removeReply(replyId);

        //when & then
        mvc.perform(
                        post("/replies/" + replyId + "/delete")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        then(replyServiceImpl).should().removeReply(replyId);
    }

}