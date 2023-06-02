package com.hillel.zakushniak.repository;

import com.hillel.zakushniak.ConnectionSingleton;
import com.hillel.zakushniak.model.Question;
import com.hillel.zakushniak.repository.dao.QuestionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepositoryPostgres implements QuestionRepository {

    private final Connection connection;
    public static final String save =
            """
                    INSERT INTO public.questions(text, topic_id) 
                    VALUES (?, ?)
                    """;

    public static final String get =
            """
                   SELECT * FROM public.questions
                    WHERE id = ?
                    """;
    public static final String getAll =
            """
                    SELECT * FROM public.questions
                    """;
    public static final String remove =
            """
                    DELETE FROM public.questions
                    WHERE id = ?
                    """;

    public static final String update =
            """
                    UPDATE public.questions
                    SET name = ?,
                    WHERE id = ?;
                    """;

    public QuestionRepositoryPostgres(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean saveQuestion(Question question) {
        try {
            var preparedStatement = connection.prepareStatement(save);

            preparedStatement.setString(1, question.getText());
            preparedStatement.setInt(2, question.getTopic_id());
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Question getQuestion(int id) {

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
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Question> getAllQuestions() {

        try {
            Statement statement = ConnectionSingleton.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(getAll);
            List<Question> questions = new ArrayList<>();

            while (resultSet.next()) {
                Question build = Question.builder()
                        .text(resultSet.getString(2))
                        .id(resultSet.getInt(1))
                        .topic_id(resultSet.getInt(3))
                        .build();

                questions.add(build);
            }
            return questions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateQuestion(Question question) {

        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(update);
            preparedStatement.setInt(1, question.getId());
            preparedStatement.setString(2, question.getText());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeQuestion(int id) {

        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(remove);

            preparedStatement.setInt(1, id);
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
