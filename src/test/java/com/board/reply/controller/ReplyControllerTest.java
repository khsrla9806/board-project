package com.board.reply.controller;

import com.board.global.exception.GlobalExceptionHandler;
import com.board.reply.dto.ReplyDto;
import com.board.reply.service.ReplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("ReplyController 테스트")
@WebMvcTest(
        controllers = ReplyController.class
)
class ReplyControllerTest {

    private final MockMvc mvc;

    @MockBean
    private GlobalExceptionHandler globalExceptionHandler;

    @MockBean
    private ReplyService replyService;

    ReplyControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("댓글 등록 - 정상 호출")
    @WithMockUser
    @Test
    public void replyList() throws Exception {
        // given
        willDoNothing().given(replyService).addReply(
                ReplyDto.ReplyRequestDto.builder()
                        .boardId(1L)
                        .parentId(null)
                        .memberId(1L)
                        .content("댓글 테스트입니다.")
                        .build()
        );

        // when & then
        mvc.perform(post("/board/reply/new")
                .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        then(replyService).should().addReply(any(ReplyDto.ReplyRequestDto.class));
    }

    @DisplayName("댓글 삭제 - 정상 호출")
    @WithMockUser
    @Test
    public void deleteReply() throws Exception {
        //given
        final Long replyId = 1L;
        willDoNothing().given(replyService).removeReply(replyId);

        //when & then
        mvc.perform(post("/board/reply/delete/" + replyId)
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        then(replyService).should().removeReply(replyId);
    }

}