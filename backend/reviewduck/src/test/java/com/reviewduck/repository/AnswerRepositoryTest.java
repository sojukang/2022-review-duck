package com.reviewduck.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.reviewduck.domain.Answer;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("답변을 저장한다.")
    void save() {
        // given
        Answer answer = new Answer("answer1");

        // when
        Answer savedAnswer = answerRepository.save(answer);

        // then
        Assertions.assertThat(savedAnswer).isSameAs(answer);
    }
}