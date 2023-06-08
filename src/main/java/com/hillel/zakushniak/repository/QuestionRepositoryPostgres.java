package com.hillel.zakushniak.repository;

import com.hillel.zakushniak.ConnectionSingleton;
import com.hillel.zakushniak.exception.DaoException;
import com.hillel.zakushniak.exception.IdNotFoundException;
import com.hillel.zakushniak.exception.TopicNotFoundException;
import com.hillel.zakushniak.model.Question;
import com.hillel.zakushniak.repository.dao.QuestionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepositoryPostgres implements QuestionRepository {
    private final Connection connection;

    public QuestionRepositoryPostgres(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Question saveQuestion(Question question) {
        String save =
                """
                        INSERT INTO public.questions(text, topic_id) 
                        VALUES (?, ?)
                        """;
        try {
            var preparedStatement = ConnectionSingleton.getConnection().prepareStatement(save, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, question.getText());
            preparedStatement.setInt(2, question.getTopicId());
            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt("id");
                question.setId(generatedId);
                return question;
            }
            return null;

        } catch (SQLException e) {
            throw new TopicNotFoundException("There are no topics with entered Id, your Question not added!", e);
        }
    }

    @Override
    public Question getQuestion(int id) {
        String get =
                """
                        SELECT * FROM public.questions
                         WHERE id = ?
                         """;

        try {
            var preparedStatement = ConnectionSingleton.getConnection().prepareStatement(get);

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return Question.builder()
                    .id(resultSet.getInt("id"))
                    .text(resultSet.getString("text"))
                    .build();

        } catch (SQLException e) {
            throw new IdNotFoundException("Question Id not found", e);
        }
    }

    public List<Question> questionsByTopic(String topicName) {
        String getByTopic =
                """
                        SELECT questions.id, questions.text, questions.topic_id, topics.name
                        FROM topics
                        JOIN questions
                        ON topics.id = questions.topic_id WHERE name = ?;
                        """;

        try {
            var preparedStatement = ConnectionSingleton.getConnection().prepareStatement(getByTopic);
            preparedStatement.setString(1, topicName);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Question> questionsByTopic = new ArrayList<>();

            while (resultSet.next()) {
                Question build = Question.builder()
                        .id(resultSet.getInt(1))
                        .text(resultSet.getString(2))
                        .topicId(resultSet.getInt(3))
                        .build();

                questionsByTopic.add(build);
            }
            return questionsByTopic;

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public List<Question> getAllQuestions() {
        String getAll =
                """
                        SELECT * FROM public.questions
                        """;

        try {
            var preparedStatement = ConnectionSingleton.getConnection().prepareStatement(getAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Question> questions = new ArrayList<>();

            while (resultSet.next()) {
                Question build = Question.builder()
                        .id(resultSet.getInt(1))
                        .text(resultSet.getString(2))
                        .topicId(resultSet.getInt(3))
                        .build();

                questions.add(build);
            }
            return questions;

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<Question> getAllRandomOrder() {
        String getAllRandomOrder =
                """
                        SELECT *
                        FROM questions
                        ORDER BY RANDOM();
                        """;

        try {
            var preparedStatement = ConnectionSingleton.getConnection().prepareStatement(getAllRandomOrder);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Question> questions = new ArrayList<>();

            while (resultSet.next()) {
                Question build = Question.builder()
                        .id(resultSet.getInt(1))
                        .text(resultSet.getString(2))
                        .build();

                questions.add(build);
            }
            return questions;

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public int updateQuestion(Question question) {
        String update =
                """
                        UPDATE public.questions
                        SET name = ?,
                        WHERE id = ?;
                        """;

        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(update);
            preparedStatement.setInt(1, question.getId());
            preparedStatement.setString(2, question.getText());

            return preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public boolean removeQuestion(int id) throws IdNotFoundException {
        String remove =
                """
                        DELETE FROM public.questions
                        WHERE id = ?
                        """;

        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(remove);

            preparedStatement.setInt(1, id);
            return preparedStatement.execute();

        } catch (SQLException throwables) {
            throw new IdNotFoundException("id not found!");
        }
    }
}