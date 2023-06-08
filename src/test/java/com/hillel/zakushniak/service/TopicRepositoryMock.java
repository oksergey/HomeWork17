package com.hillel.zakushniak.service;

import com.hillel.zakushniak.model.Topic;
import com.hillel.zakushniak.repository.dao.TopicRepository;

import java.util.ArrayList;
import java.util.List;

public class TopicRepositoryMock implements TopicRepository {

    public List<Topic> topics = new ArrayList<>();

    @Override
    public Topic saveTopic(Topic topic) {
        return false;
    }

    @Override
    public Topic getTopic(int id) {
        for (Topic topic : topics) {
            if (topic.getId() == id) return topic;
        }
        return null;
    }

    @Override
    public List<Topic> getAllTopics() {
        return topics;
    }

    @Override
    public boolean removeTopic(int id) {
        return false;
    }

    @Override
    public int updateTopic(Topic topic) {
        return 0;
    }
}
