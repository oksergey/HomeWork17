package com.hillel.zakushniak.service;

import com.hillel.zakushniak.model.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class QuestionServiceTest {

    private QuestionRepositoryMock questionRepositoryMock = new QuestionRepositoryMock();
    private TopicRepositoryMock topicRepositoryMock = new TopicRepositoryMock();

    @Test
    void saveQuestionTest() {
        QuestionService questionService = new QuestionService(questionRepositoryMock);

        Question expected = Question.builder()
                .text("Question1").topic_id(1).build();

        Assertions.assertEquals(expected, questionService.saveQuestion("Question1", 1));
    }

    @Test
    void removeQuestionTest() {
        QuestionService questionService = new QuestionService(questionRepositoryMock);

        questionRepositoryMock.questions.add(Question.builder().id(1).text("Question1").build());
        questionService.removeQuestion(1);

        Assertions.assertTrue(questionRepositoryMock.questions.isEmpty());
    }

    @Test
    void getAllQuestionsTest() {
        QuestionService questionService = new QuestionService(questionRepositoryMock);
        questionRepositoryMock.questions.add(Question.builder().text("Question1").build());
        questionRepositoryMock.questions.add(Question.builder().text("Question2").build());
        int expected = 2;

        Assertions.assertEquals(expected, questionService.getAllQuestions().size());
    }

    @Test
    void getRandomTest() {
        QuestionService questionService = new QuestionService(questionRepositoryMock);

        Question expected = Question.builder().id(1).text("QuestionText").topic_id(1).build();
        questionRepositoryMock.questions.add(expected);

        Assertions.assertEquals(expected, questionService.getRandomByTopic("QuestionText"));
    }

    @Test
    public void getRandomByTopicTest() {
        QuestionService questionService = new QuestionService(questionRepositoryMock);

        Question expected = Question.builder().id(1).text("QuestionText").topic_id(1).build();
        questionRepositoryMock.questions.add(expected);

        Assertions.assertEquals(expected, questionService.getRandomByTopic("QuestionText"));
    }
}