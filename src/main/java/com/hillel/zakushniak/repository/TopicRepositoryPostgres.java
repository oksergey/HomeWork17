package com.hillel.zakushniak.repository;

import com.hillel.zakushniak.model.Topic;
import com.hillel.zakushniak.repository.dao.TopicRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicRepositoryPostgres implements TopicRepository {

    private final Connection connection;

    public static final String save =
            """
                    INSERT INTO public.topics (name)
                    VALUES (?)
                    """;

    public static final String get =
            """
                   SELECT * FROM public.topics
                    WHERE id = ?
                    """;
    public static final String getAll =
            """
                    SELECT * FROM public.topics
                    """;


    public static final String remove =
            """
                    DELETE FROM public.topics
                    WHERE id = ?
                    """;

    public static final String update =
            """
                    UPDATE public.topics
                    SET name = ?,
                    WHERE id = ?;
                    """;

    public TopicRepositoryPostgres(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean saveTopic(Topic topic) {
        try {
            var preparedStatement = connection.prepareStatement(save);

            preparedStatement.setString(1, topic.getName());
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Topic getTopic(int id) {

        try {
            var preparedStatement = connection.prepareStatement(get);
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
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAll);
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

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setInt(1, topic.getId());
            preparedStatement.setString(2, topic.getName());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeTopic(int id) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(remove);

            preparedStatement.setInt(1, id);
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
