package com.hillel.zakushniak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {

    private static Connection connection;
    private static final String name = "postgres";
    private static final String password = "postgres";
    public static final String url = "jdbc:postgresql://localhost:5432/dvdrental";


    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, name, password);
        }

        return connection;
    }

}

