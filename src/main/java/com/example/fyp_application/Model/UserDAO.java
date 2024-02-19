package com.example.fyp_application.Model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {


    private Connection connection() {
        String url = "jdbc:sqlite:/D:\\FYP_Application\\src\\main\\resources\\db\\ISAMDB.db";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ObservableList<UserModel> getAllUsers() {
        String sql = "SELECT * FROM users";
        ObservableList<UserModel> users = FXCollections.observableArrayList(); // Correctly initialize the list
        try (Connection conn = this.connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UserModel user = new UserModel(
                        rs.getInt("UserID"),
                        rs.getInt("userRoleID"),
                        rs.getInt("deptID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("DOB"),
                        rs.getString("Email"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Phone"),
                        rs.getString("AccountStatus"),
                        rs.getString("Photo"),
                        rs.getString("CreatedAt"),
                        rs.getString("ExpiresAt")
                );
                users.add(user); // Add the user to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}


/*    public List<UserModel> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<UserModel> users = new ArrayList<>();
        try (Connection conn = this.connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new UserModel(
                        rs.getInt("userID"),
                        rs.getInt("userRoleID"),
                        rs.getInt("deptID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("accountStatus"),
                        rs.getString("profilePicture"),
                        rs.getString("createdAt"),
                        rs.getString("expiresAt")
                ));


            }
        System.out.println(users);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }*/

