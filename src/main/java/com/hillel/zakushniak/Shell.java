package com.hillel.zakushniak;

import com.hillel.zakushniak.model.Question;
import com.hillel.zakushniak.model.Topic;
import com.hillel.zakushniak.service.QuestionService;
import com.hillel.zakushniak.service.TopicService;

import java.util.Scanner;

public class Shell {
    private final QuestionService questionService;
    private final TopicService topicService;
    private final Scanner scanner;

    public Shell(QuestionService questionService, TopicService topicService, Scanner scanner) {
        this.questionService = questionService;
        this.topicService = topicService;
        this.scanner = scanner;
    }

    public Runnable getRandomQuestionByTopic() {
        return () -> {
            System.out.println("Please enter name of Topic: ");
            System.out.println(topicService.getAllTopics());
            String topicAsking = scanner.nextLine();

            for (Topic topic : topicService.getAllTopics()) {
                if (topic.getName().equals(topicAsking)) {
                    Question question = questionService.getRandomByTopic(topicAsking);
                    if (question == null) {
                        System.out.println("This topic is empty, please fill it with questions");
                        return;
                    } else System.out.printf("The question is: %s ", question.getText());
                    System.out.println();
                    return;
                }
            }
            System.out.println("Topic is not found!");
            System.out.println();
        };
    }
    public Runnable getAllTopics() {
        return () -> {
            System.out.println("\n" + "Here is full list of topics:");
            System.out.println(topicService.getAllTopics());
            System.out.println();
        };
    }
    public Runnable saveQuestion() {
        return () -> {
            System.out.println("Please input new question text:");
            String text = scanner.nextLine();

            int topicId = 0;
            boolean isInputValid = false;
            do {
                System.out.println("Please input topic id for this question:");
                try {
                    topicId = Integer.parseInt(scanner.nextLine());
                    isInputValid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            } while (!isInputValid);
            System.out.println(questionService.saveQuestion(text, topicId) + " saved.");
            System.out.println();
        };
    }
    public Runnable saveTopic() {
        return () -> {
            System.out.println("Please input new Topic name:");
            String name = scanner.nextLine();
            Topic saved = topicService.saveTopic(name);
            System.out.println(saved == null ? "Topic is already exist!" : saved);
            System.out.println();

        };
    }
    public Runnable getRandom() {
        return () -> {
            System.out.println("Here is random question from all topics:");
            System.out.println(questionService.getRandom());
            System.out.println();
        };
    }
    public Runnable removeQuestion() {
        return () -> {

            boolean isInputValid = false;
            int questionId = 0;
            do {
                System.out.println("Please input Question id for removing: ");
                try {
                    questionId = Integer.parseInt(scanner.nextLine());
                    isInputValid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            } while (!isInputValid);

            if (questionService.getQuestion(questionId) != null)
                System.out.println(questionService.getQuestion(questionId) + " was deleted.");

            questionService.removeQuestion(questionId);
            System.out.println();
        };
    }
}
