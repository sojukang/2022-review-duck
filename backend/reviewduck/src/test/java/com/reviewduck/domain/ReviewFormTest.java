package com.reviewduck.domain;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReviewFormTest {

    @Test
    @DisplayName("질문 목록을 받아 리뷰 폼을 생성한다.")
    void create() {
        // given
        List<Question> questions = List.of(
            new Question(1L, "q1"), new Question(2L, "q2")
        );

        // when
        ReviewForm reviewForm = new ReviewForm(questions);

        // then
        List<Question> actual = reviewForm.getQuestions();
        Assertions.assertThat(actual).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(questions);
    }
}
