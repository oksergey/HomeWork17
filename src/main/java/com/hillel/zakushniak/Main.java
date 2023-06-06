package com.hillel.zakushniak;

import com.hillel.zakushniak.repository.QuestionRepositoryPostgres;
import com.hillel.zakushniak.repository.TopicRepositoryPostgres;
import com.hillel.zakushniak.service.QuestionService;
import com.hillel.zakushniak.service.TopicService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello world!");

        TopicRepositoryPostgres topicRepository = new TopicRepositoryPostgres(ConnectionSingleton.getConnection());
        QuestionRepositoryPostgres questionRepository = new QuestionRepositoryPostgres(ConnectionSingleton.getConnection());
        TopicService topicService = new TopicService(topicRepository);
        QuestionService questionService = new QuestionService(questionRepository);

        questionService.removeQuestion(31);
        //        System.out.println(topicService.getAllTopics());

        //    System.out.println(questionService.getRandomByTopic("Introduction to Java"));

        //        topicService.saveTopic("BlaBla");
        //        questionService.saveQuestion("What is Blabalblablabal?", 6);

    }
}