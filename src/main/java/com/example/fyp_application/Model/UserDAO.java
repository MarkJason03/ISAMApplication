package com.example.fyp_application.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {


    private Connection connection(){
        String url = "jdbc:sqlite:/D:\\FYP_Application\\src\\main\\resources\\db\\ISAMDB.db";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

   /* public List<UserModel> getAllUsers() {
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
}
