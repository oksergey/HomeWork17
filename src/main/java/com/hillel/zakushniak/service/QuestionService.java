package com.hillel.zakushniak.service;

import com.hillel.zakushniak.exception.IdNotFoundException;
import com.hillel.zakushniak.exception.TopicNotFoundException;
import com.hillel.zakushniak.model.Question;
import com.hillel.zakushniak.repository.dao.QuestionRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class QuestionService {
    public final QuestionRepository questionRepository;
    public static final Random random = new Random();
    private List<Question> allByRandomOrder;
    private Iterator<Question> iterator;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question saveQuestion(String text, int topic_id) {
        Question question = Question.builder()
                .text(text).topicId(topic_id).build();
        try {
            return questionRepository.saveQuestion(question);

        } catch (TopicNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return question;
    }

    public Question getQuestion(int id) {

        try {
            return questionRepository.getQuestion(id);

        } catch (IdNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.getAllQuestions();
    }

    public Question getRandom() {
        if (iterator == null || !iterator.hasNext()) {
            allByRandomOrder = questionRepository.getAllRandomOrder();
            iterator = allByRandomOrder.iterator();
        }
        return iterator.next();
    }

    public Question getRandomByTopic(String name) {
        try {
            List<Question> allFromTopic = questionRepository.questionsByTopic(name);
            Question question = allFromTopic.get(random.nextInt(allFromTopic.size()));
            return question;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public boolean removeQuestion(int id) {

        return questionRepository.removeQuestion(id);
    }

}
