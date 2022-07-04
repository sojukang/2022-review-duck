package com.reviewduck.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.reviewduck.domain.Answer;
import com.reviewduck.domain.Question;
import com.reviewduck.domain.Review;
import com.reviewduck.domain.User;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    @DisplayName("존재하는 질문에서 리뷰를 저장한다.")
    void saveFromTemplate() {
        // given
        User user = new User(1L, "user1");
        userRepository.save(user);

        Question q1 = new Question(1L, "q1");
        Question q2 = new Question(2L, "q2");
        questionRepository.save(q1);
        questionRepository.save(q2);

        Review review = new Review(user, Map.of(
            q1, new Answer("a1"),
            q2, new Answer("a2")
        ));

        // when
        Review savedReview = reviewRepository.save(review);

        // then
        List<Answer> answers = answerRepository.findAll();
        assertAll(
            () -> assertThat(answers).isNotEmpty(),
            () -> assertThat(savedReview).isSameAs(review)
        );
    }
}