package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginModel {
    static Connection connection;

    public LoginModel() {
        connection = DatabaseConnectionHandler.getConnection();
        if (connection == null) {
            System.out.println("Database Connection Failed");
            System.exit(1);
        }
    }



    public static boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

/*
    public boolean isLogin(String user, String pass) throws SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "SELECT tbl_Users.UserID, tbl_Users.Username, tbl_Users.Password, tbl_Users.userRoleID\n" +
                "FROM tbl_Users\n" +
                "JOIN tbl_userRoles ON tbl_Users.userRoleID = tbl_userRoles.userRoleID\n" +
                "WHERE tbl_Users.Username = \"admin\" AND tbl_Users.Password = \"admin\";\n";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;

        }
    }*/
}
