package com.hillel.zakushniak.service;

import com.hillel.zakushniak.model.Topic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TopicServiceTest {

    private TopicRepositoryMock topicRepositoryMock = new TopicRepositoryMock();

    @Test
    void saveTopicTest() {
        Topic topic = Topic.builder().id(1).name("OOP").build();
        topicRepositoryMock.topics.add(topic);

        Assertions.assertTrue(topicRepositoryMock.topics.contains(topic));
    }

    @Test
    void getTopicTest() {
        TopicService topicService = new TopicService(topicRepositoryMock);
        Topic topic = Topic.builder().name("OOP").build();
        topicRepositoryMock.topics.add(topic);
        Topic expected = topicService.getTopic(topic.getId());
        Assertions.assertEquals(topic, expected);
    }

    @Test
    void getAllTopicsTest() {
        TopicService topicService = new TopicService(topicRepositoryMock);
        topicRepositoryMock.topics.add(Topic.builder().name("Topic1").build());
        topicRepositoryMock.topics.add(Topic.builder().name("Topic2").build());
        int expected = 2;

        Assertions.assertEquals(expected, topicService.getAllTopics().size());
    }
}