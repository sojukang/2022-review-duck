package com.reviewduck.domain;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "questions_answers",
        joinColumns = {@JoinColumn(name = "review_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "answer_id", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "question_id")
    private Map<Question, Answer> values;

    public Review(User user, Map<Question, Answer> answersByQuestions) {
        this.user = user;
        this.values = new LinkedHashMap<>(answersByQuestions);
    }

    public static Review of(User user, Map<Question, Answer> answersByQuestions) {
        return new Review(user, answersByQuestions);
    }

    public Map<Question, Answer> getAnswersByQuestions() {
        return Map.copyOf(values);
    }

    public void update(Map<Question, Answer> answersByQuestions) {
        this.values = Map.copyOf(answersByQuestions);
    }
}
