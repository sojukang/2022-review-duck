package com.reviewduck.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.reviewduck.domain.Question;
import com.reviewduck.domain.ReviewForm;

@DataJpaTest
class ReviewFormRepositoryTest {

    @Autowired
    private ReviewFormRepository reviewFormRepository;

    @Test
    @DisplayName("리뷰 폼을 저장한다.")
    void save() {
        // given
        ReviewForm reviewForm = new ReviewForm(List.of(new Question("q1"), new Question("q2")));

        // when
        ReviewForm savedReviewForm = reviewFormRepository.save(reviewForm);

        // then
        Assertions.assertThat(savedReviewForm).isSameAs(reviewForm);
    }
}