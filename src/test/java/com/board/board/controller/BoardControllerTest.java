package com.board.board.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("[POST] 게시글 생성 성공")
    @Transactional
    void createBoardSuccess() throws Exception {
        // Given
        String title = "게시글 제목";
        String content = "게시글 본문";


        // When
        ResultActions resultActions = mvc.perform(multipart("/board")
                .file("thumbnail", null)
                .param("title", title)
                .param("content", content));

        // Then
        resultActions.andExpect(status().isFound());
        resultActions.andExpect(header().exists("Location"));
    }

    @Test
    @DisplayName("[POST] 게시글 생성 실패 - 제목 Null")
    @Transactional
    void createBoardFailWithNoneTitle() throws Exception {
        // Given
        String title = null;
        String content = "게시글 본문";

        // When
        ResultActions resultActions = mvc.perform(multipart("/board")
                .file("thumbnail", null)
                .param("title", title)
                .param("content", content));

        // Then
        resultActions.andExpect(model().attributeHasFieldErrors("board", "title"));
    }

    @Test
    @DisplayName("[POST] 게시글 생성 실패 - 내용 Null")
    @Transactional
    void createBoardFailWithNoneContent() throws Exception {
        // Given
        String title = "게시글 제목";
        String content = null;

        // When
        ResultActions resultActions = mvc.perform(multipart("/board")
                .file("thumbnail", null)
                .param("title", title)
                .param("content", content));

        // Then
        resultActions.andExpect(model().attributeHasFieldErrors("board", "content"));
    }

}