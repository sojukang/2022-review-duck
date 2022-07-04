package com.reviewduck.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reviewduck.domain.Answer;
import com.reviewduck.domain.Question;
import com.reviewduck.domain.Review;
import com.reviewduck.domain.User;
import com.reviewduck.repository.ReviewRepository;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Long save(User user, Map<Question, Answer> answersByQuestions) {
        return reviewRepository.save(new Review(user, answersByQuestions)).getId();
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다.")
            );
    }

    public void update(Long id, Map<Question, Answer> answersByQuestions) {
        Review review = findById(id);
        review.update(answersByQuestions);
    }
}
