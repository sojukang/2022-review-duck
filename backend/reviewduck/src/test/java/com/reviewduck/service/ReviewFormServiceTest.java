package com.reviewduck.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.reviewduck.domain.Question;
import com.reviewduck.domain.ReviewForm;

@SpringBootTest
@Transactional
public class ReviewFormServiceTest {

    @Autowired
    private ReviewFormService reviewFormService;

    @Test
    @DisplayName("리뷰 폼을 저장한다.")
    void save() {
        // given
        List<Question> questions = List.of(new Question("q1"), new Question("q2"));

        // when
        Long savedId = reviewFormService.save(questions);

        // then
        assertThat(savedId).isNotNull();
    }

    @Test
    @DisplayName("리뷰 폼을 조회한다.")
    void findById() {
        // given
        List<Question> questions = List.of(new Question("q1"), new Question("q2"));
        Long savedId = reviewFormService.save(questions);

        // when
        ReviewForm actual = reviewFormService.findById(savedId);

        // then
        assertThat(actual.getQuestions())
            .usingRecursiveComparison()
            .isEqualTo(questions);
    }

    @Test
    @DisplayName("리뷰 폼을 수정한다.")
    void update() {
        // given
        List<Question> questions = List.of(new Question("q1"), new Question("q2"));
        Long savedId = reviewFormService.save(questions);

        List<Question> modifiedQuestions = List.of(new Question(2L, "q2modified"),
            new Question(1L, "q1modified"), new Question("q3"));

        // when
        reviewFormService.update(savedId, modifiedQuestions);

        ReviewForm updatedReviewForm = reviewFormService.findById(savedId);

        // then
        boolean hasNullId = updatedReviewForm.getQuestions().stream()
            .anyMatch(Question::hasNullId);

        assertAll(
            () -> assertThat(hasNullId).isFalse(),
            () -> assertThat(updatedReviewForm.getQuestions()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(modifiedQuestions)
        );
    }
}
