package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO {

    public List<UserRoleModel> getAllRoles() {
        List<UserRoleModel> roles = new ArrayList<>();
        String sql = "select * from tbl_userRoles";

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    UserRoleModel role = new UserRoleModel(
                            resultSet.getInt("userRoleID"),
                            resultSet.getString("userRoleName"));
                    roles.add(role);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }



        return roles;
    }



}
