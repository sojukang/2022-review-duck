package com.reviewduck.domain;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewTest {

    @Test
    @DisplayName("리뷰를 생성한다.")
    void create() {
        // given
        Question question1 = new Question(1L, "q1");
        Question question2 = new Question(2L, "q2");
        Answer answer1 = new Answer("a1");
        Answer answer2 = new Answer("a2");
        User user = new User("user1");

        // when
        Map<Question, Answer> answersByQuestions = Map.of(question1, answer1, question2, answer2);
        Review review = Review.of(user, answersByQuestions);

        // then
        Map<Question, Answer> actual = review.getAnswersByQuestions();

        Assertions.assertThat(actual).isEqualTo(answersByQuestions);
    }
}
