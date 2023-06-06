package com.hillel.zakushniak.repository.dao;

import java.util.List;

public interface TopicRepository {
    boolean saveTopic(Topic topic);

    Topic getTopic(int id);

    List <Topic> getAllTopics();

    boolean removeTopic(int id);

    int updateTopic(Topic topic);

//    List<Question> getRandomByTopic();

}
