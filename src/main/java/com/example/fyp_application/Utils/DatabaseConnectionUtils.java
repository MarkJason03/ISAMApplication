package com.example.fyp_application.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
//Helper class to handle database connections
public class DatabaseConnectionUtils {


    private static final String dbUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/resources/db/ISAMDB.db";

    private DatabaseConnectionUtils(){

    }
    public static Connection getConnection() {
        try{

            return DriverManager.getConnection(dbUrl);

        } catch (SQLException e) {
            return null;
        }
    }


    public static boolean isDbConnected(Connection connection) {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static <T> List<T> executeQuery(String sql, Function<ResultSet, T> mapper, Object... params) {
        List<T> results = new ArrayList<>();

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(mapper.apply(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }


}

