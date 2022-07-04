package com.reviewduck.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.reviewduck.domain.Answer;
import com.reviewduck.domain.Question;
import com.reviewduck.domain.Review;
import com.reviewduck.domain.User;

@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewFormService reviewFormService;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("리뷰를 저장한다.")
    void save() {
        // given
        Long questionsId = reviewFormService.save(List.of(new Question("q1"), new Question("q2")));
        List<Question> questions = reviewFormService.findById(questionsId).getQuestions();

        Question question1 = questions.get(0);
        Question question2 = questions.get(1);

        Answer answer1 = new Answer("a1");
        Answer answer2 = new Answer("a2");

        Long userId = userService.save("user1");
        User user = userService.findById(userId);

        Map<Question, Answer> answersByQuestions = Map.of(question1, answer1, question2, answer2);

        // when
        Long savedId = reviewService.save(user, answersByQuestions);

        // then
        assertThat(savedId).isNotNull();
    }

    @Test
    @DisplayName("리뷰 답변을 수정한다.")
    void update() {
        // given
        Long questionsId = reviewFormService.save(List.of(new Question("q1"), new Question("q2")));
        List<Question> questions = reviewFormService.findById(questionsId).getQuestions();

        Question question1 = questions.get(0);
        Question question2 = questions.get(1);

        Long userId = userService.save("user1");
        User user = userService.findById(userId);

        Answer answer1 = new Answer("a1");
        Answer answer2 = new Answer("a2");

        Map<Question, Answer> answersByQuestions = Map.of(question1, answer1, question2, answer2);
        Long savedId = reviewService.save(user, answersByQuestions);

        // when
        Answer updatedAnswer1 = new Answer(answer1.getId(), "a1modified");
        Answer updatedAnswer2 = new Answer(answer2.getId(), "a2modified");

        Map<Question, Answer> UpdatedAnswersByQuestions = Map.of(
            question1, updatedAnswer1, question2, updatedAnswer2);

        reviewService.update(savedId, UpdatedAnswersByQuestions);

        // then
        Review updatedReview = reviewService.findById(savedId);
        assertThat(updatedReview.getAnswersByQuestions()).isEqualTo(UpdatedAnswersByQuestions);
    }
}
