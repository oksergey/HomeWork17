package com.hillel.zakushniak;

import com.hillel.zakushniak.repository.QuestionRepositoryPostgres;
import com.hillel.zakushniak.repository.TopicRepositoryPostgres;
import com.hillel.zakushniak.service.QuestionService;
import com.hillel.zakushniak.service.TopicService;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        TopicRepositoryPostgres topicRepository = new TopicRepositoryPostgres(ConnectionSingleton.getConnection());
        QuestionRepositoryPostgres questionRepository = new QuestionRepositoryPostgres(ConnectionSingleton.getConnection());
        TopicService topicService = new TopicService(topicRepository);
        QuestionService questionService = new QuestionService(questionRepository);
        Scanner scanner = new Scanner(System.in);
        Map<Command, Runnable> command = init(scanner);

        while (true) {
            System.out.println("Please input command from the list:");
            for (Command value : Command.values()) {
                System.out.println(value);
            }

            String userInput = scanner.nextLine().toUpperCase();

            for (Command value : Command.values()) {
                if (value.toString().equals(userInput)) {
                    command.getOrDefault(Command.valueOf(userInput), () -> System.out.println("incorrect command, try again!")).run();
                }
            }
        }
    }

    static Map<Command, Runnable> init(Scanner scanner) {
        Shell shell = new Shell(
                new QuestionService(new QuestionRepositoryPostgres(ConnectionSingleton.getConnection())),
                new TopicService(new TopicRepositoryPostgres(ConnectionSingleton.getConnection())),
                scanner);
        return Map.of(
                Command.GET_ALL_TOPICS, shell.getAllTopics(),
                Command.GET_RANDOM_BY_TOPIC, shell.getRandomQuestionByTopic(),
                Command.GET_RANDOM, shell.getRandom(),
                Command.SAVE_TOPIC, shell.saveTopic(),
                Command.SAVE_QUESTION, shell.saveQuestion(),
                Command.REMOVE_QUESTION, shell.removeQuestion()
        );
    }


}