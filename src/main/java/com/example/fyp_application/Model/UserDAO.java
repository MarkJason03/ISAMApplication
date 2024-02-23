package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseHandler;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.sql.*;

public class UserDAO {

    public ObservableList<UserModel> getAllUsers() {
        ObservableList<UserModel> arrayList = FXCollections.observableArrayList();

        String sql = "SELECT tbl_Users.UserID, FirstName,LastName,Gender,Email,Username,Phone,AccountStatus,CreatedAt,ExpiresOn, \n" +
                "       tbl_userRoles.userRoleName As Role,\n" +
                "       tbl_Departments.deptName AS departmentName\n" +
                "FROM tbl_Users\n" +
                "JOIN tbl_userRoles ON tbl_Users.userRoleID = tbl_userRoles.userRoleID\n" +
                "JOIN tbl_Departments ON tbl_Users.deptID = tbl_Departments.deptID;";


        UserModel userModel;

        try{
            Connection connect = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                userModel = new UserModel(
                        resultSet.getInt("UserID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Gender"),
                        resultSet.getString("Email"),
                        resultSet.getString("Username"),
                        resultSet.getString("Phone"),
                        resultSet.getString("AccountStatus"),
                        resultSet.getString("CreatedAt"),
                        resultSet.getString("ExpiresOn"),
                        resultSet.getString("Role"),
                        resultSet.getString("departmentName"));
                arrayList.add(userModel);
            };

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


    public void deleteUser(int userID) {
        String sql = "DELETE FROM tbl_Users WHERE UserID = ?";

        try {
            //try getting connection from the DatabaseHandler
            Connection connection = DatabaseHandler.getConnection();
            assert connection != null;
            //prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //delete the user with the given userID
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int countActiveUsers(){

        int count = 0;

        String sql = "select count (UserID)\n" +
                "from tbl_Users\n" +
                "where AccountStatus = \"Active\"\n ";

        try{

            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                count = resultSet.getInt(1);
            }

            connection.close();


        } catch (SQLException e){
            e.printStackTrace();
        }

        return count;
    }


    public int countInactiveUsers(){

        int count = 0;

        String sql = "Select Count (UserID) FROM tbl_Users Where AccountStatus='Inactive'; ";

        try{

            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                count = resultSet.getInt(1);
            }
            connection.close();


        } catch (SQLException e){
            e.printStackTrace();
        }

        return count;
    }
}


