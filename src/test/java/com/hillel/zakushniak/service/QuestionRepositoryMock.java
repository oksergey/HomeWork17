package com.hillel.zakushniak.service;

import com.hillel.zakushniak.model.Question;
import com.hillel.zakushniak.repository.dao.QuestionRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class QuestionRepositoryMock implements QuestionRepository {

    public static final Random random = new Random();
    public List<Question> questions = new ArrayList<>();

    @Override
    public Question saveQuestion(Question question) {
        return question;
    }

    @Override
    public Question getQuestion(int id) {
        return null;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questions;
    }

    @Override
    public int updateQuestion(Question question) {
        return 0;
    }

    @Override
    public boolean removeQuestion(int id) {
        Iterator<Question> iterator = questions.iterator();
        while (iterator.hasNext()) {
            Question question = iterator.next();
            if (question.getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Question> questionsByTopic(String name) {

        return List.of(Question.builder().id(1).text("QuestionText").topic_id(1).build());
    }

    @Override
    public List<Question> getAllRandomOrder() {
        return null;
    }
}
