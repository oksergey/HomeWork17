package com.hillel.zakushniak.repository;

import com.hillel.zakushniak.ConnectionSingleton;
import com.hillel.zakushniak.model.Topic;
import com.hillel.zakushniak.repository.dao.TopicRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicRepositoryPostgres implements TopicRepository {

    private final Connection connection;

    public TopicRepositoryPostgres(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean saveTopic(Topic topic) {
        String save =
                """
                        INSERT INTO public.topics (name)
                        SELECT ?
                        WHERE NOT EXISTS (SELECT 1 FROM public.topics WHERE name = ?) 
                        """;

        try {
            var preparedStatement = ConnectionSingleton.getConnection().prepareStatement(save);

            preparedStatement.setString(1, topic.getName());
            preparedStatement.setString(2, topic.getName());
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Topic getTopic(int id) {
        String get =
                """
                        SELECT * FROM public.topics
                         WHERE id = ?
                         """;
        try {
            var preparedStatement = ConnectionSingleton.getConnection().prepareStatement(get);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return Topic.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Topic> getAllTopics() {
        String getAll =
                """
                        SELECT * FROM public.topics
                        """;
        try {
            var preparedStatement = ConnectionSingleton.getConnection().prepareStatement(getAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Topic> topics = new ArrayList<>();

            while (resultSet.next()) {
                Topic build = Topic.builder()
                        .name(resultSet.getString(2))
                        .id(resultSet.getInt(1))
                        .build();

                topics.add(build);
            }
            return topics;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateTopic(Topic topic) {
        String update =
                """
                        UPDATE public.topics
                        SET name = ?,
                        WHERE id = ?;
                        """;

        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(update);
            preparedStatement.setInt(1, topic.getId());
            preparedStatement.setString(2, topic.getName());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeTopic(int id) {
        String remove =
                """
                        DELETE FROM public.topics
                        WHERE id = ?
                        """;
        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(remove);

            preparedStatement.setInt(1, id);
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}