package com.hillel.zakushniak.service;

import com.hillel.zakushniak.model.Topic;
import com.hillel.zakushniak.repository.dao.TopicRepository;
import java.util.List;

public class TopicService {

    public final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public boolean saveTopic(String name) {
        Topic topic = Topic.builder().name(name).build();

        return topicRepository.saveTopic(topic);
    }
    public Topic getTopic(int id){
        return topicRepository.getTopic(id);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.getAllTopics();
    }



}
