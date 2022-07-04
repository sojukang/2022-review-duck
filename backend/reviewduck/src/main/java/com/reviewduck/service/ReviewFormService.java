package com.reviewduck.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reviewduck.domain.Question;
import com.reviewduck.domain.ReviewForm;
import com.reviewduck.repository.ReviewFormRepository;

@Service
@Transactional
public class ReviewFormService {

    private final ReviewFormRepository reviewFormRepository;
    private final QuestionService questionService;

    public ReviewFormService(ReviewFormRepository reviewFormRepository,
        QuestionService questionService) {
        this.reviewFormRepository = reviewFormRepository;
        this.questionService = questionService;
    }

    public Long save(List<Question> questions) {
        ReviewForm reviewForm = new ReviewForm(questions);
        return reviewFormRepository.save(reviewForm).getId();
    }

    @Transactional(readOnly = true)
    public ReviewForm findById(Long id) {
        return reviewFormRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 리뷰 폼입니다.")
        );
    }

    public void update(Long id, List<Question> questions) {
        ReviewForm reviewForm = findById(id);
        reviewForm.updateQuestions(getPersistedQuestions(questions));
    }

    private List<Question> getPersistedQuestions(List<Question> questions) {
        List<Question> newQuestions = new ArrayList<>();
        for (Question question : questions) {
            addNewQuestion(newQuestions, question);
        }
        return newQuestions;
    }

    private void addNewQuestion(List<Question> newQuestions, Question question) {
        if (Objects.isNull(question.getId())) {
            Long savedId = questionService.save(question.getValue());
            newQuestions.add(new Question(savedId, question.getValue()));
            return;
        }
        newQuestions.add(question);
    }
}
