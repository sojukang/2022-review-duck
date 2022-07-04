package com.reviewduck.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reviewduck.dto.ReviewFormCreateRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("회고 제목에 빈 값이 들어갈 경우 예외가 발생한다.")
    void emptyReviewTitle(String title) throws Exception {
        // given
        ReviewFormCreateRequest request = new ReviewFormCreateRequest(title, List.of());

        // when, then
        assertBadRequestFromPost("/api/review-forms", request, "회고 폼의 제목은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("회고 질문 목록에 null 값이 들어갈 경우 예외가 발생한다.")
    void nullQuestionList(List<String> questions) throws Exception {
        // given
        ReviewFormCreateRequest request = new ReviewFormCreateRequest("title", questions);

        // when, then
        assertBadRequestFromPost("/api/review-forms", request, "회고 폼의 질문 목록 생성 중 오류가 발생했습니다.");
    }

    void assertBadRequestFromPost(String uri, Object request, String errorMessage) throws Exception {
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }
}