package com.reviewduck.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.reviewduck.domain.Question;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("질문을 저장한다.")
    void save() {
        // given
        Question question = new Question("q1");

        // when
        Question savedQuestion = questionRepository.save(question);

        // then
        Assertions.assertThat(savedQuestion).isSameAs(question);
    }

}