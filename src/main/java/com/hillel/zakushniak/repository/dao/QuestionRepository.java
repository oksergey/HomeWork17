package com.hillel.zakushniak.repository.dao;

import java.util.List;

public interface QuestionRepository {
    Question getQuestion(int id);

    List<Question> getAllQuestions();

    Question saveQuestion(Question question);

    boolean removeQuestion(int id);

    int updateQuestion(Question question);

    List<Question> questionsByTopic(String name);

    List<Question> getAllRandomOrder();
}
