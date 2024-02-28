package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;
import com.example.fyp_application.Utils.PasswordHashHandler;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.sql.*;

public class UserDAO {


    public boolean isUsernameTaken(String Username){

        String sql = "SELECT tbl_Users.Username from tbl_Users where Username = ?";
        boolean isUsernameTaken = false;

        try{
            Connection connection = DatabaseConnectionHandler.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                isUsernameTaken = true;
            }
            DatabaseConnectionHandler.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();

    }

        return isUsernameTaken;
    }




    public boolean validateLoginCredentials(String username, String password) {
        String sql = "SELECT Password FROM tbl_Users WHERE Username = ?";
        boolean isValidAccount = false;

        try (Connection connection = DatabaseConnectionHandler.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("Password");
                    isValidAccount = PasswordHashHandler.verifyPassword(hashedPassword, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValidAccount;
    }


    public UserModel cacheUserLoginID(String username) {
        String sql = """
            SELECT UserID, FirstName, Photo, tbl_userRoles.userRoleName
                    FROM tbl_Users\s
                    JOIN tbl_userRoles ON tbl_Users.userRoleID = tbl_userRoles.userRoleID\s
                    WHERE tbl_Users.Username = ?
                 """;

        try (Connection connection = DatabaseConnectionHandler.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new UserModel(
                            resultSet.getInt("UserID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("userRoleName"),
                            resultSet.getString("Photo")
                    );
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return null;
    }


    public ObservableList<UserModel> getAllUsers() {
        ObservableList<UserModel> arrayList = FXCollections.observableArrayList();

        String sql = "SELECT tbl_Users.UserID, FirstName,LastName,Gender,Email,Username,Phone,AccountStatus,CreatedAt,ExpiresOn, \n" +
                "       tbl_userRoles.userRoleName As Role,\n" +
                "       tbl_Departments.deptName AS departmentName\n" +
                "FROM tbl_Users\n" +
                "JOIN tbl_userRoles ON tbl_Users.userRoleID = tbl_userRoles.userRoleID\n" +
                "JOIN tbl_Departments ON tbl_Users.deptID = tbl_Departments.deptID;";


        UserModel userModel;

        try {
            Connection connect = DatabaseConnectionHandler.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
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
            }

            DatabaseConnectionHandler.closeConnection(connect);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


    public void deleteUser(int userID) {
        String sql = "DELETE FROM tbl_Users WHERE UserID = ?";

        try {
            //try getting connection from the DatabaseConnectionHandler
            Connection connection = DatabaseConnectionHandler.getConnection();
            assert connection != null;
            //prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //delete the user with the given userID
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
            DatabaseConnectionHandler.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countActiveUsers() {

        int count = 0;

        String sql = "select count (UserID)\n" +
                "from tbl_Users\n" +
                "where AccountStatus = \"Active\"\n ";

        try {

            Connection connection = DatabaseConnectionHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }


/*    public void loadCurrentLoggedUser(int userID, UserModel userModel){

        String sql = "SELECT * FROM tbl_Users WHERE UserID = ?";

        try {
            Connection connection = DatabaseConnectionHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
        } catch ( SQLException e){
            e.printStackTrace();
        }

    }*/

    public int countInactiveUsers() {

        int count = 0;

        String sql = "Select Count (UserID) FROM tbl_Users Where AccountStatus='Inactive'; ";

        try {

            Connection connection = DatabaseConnectionHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }


    public UserModel loadLoggedInUserDetails(Integer userID) throws SQLException {
        String sql = """
                SELECT UserID, FirstName , LastName , Email, Gender,Photo, Phone, DOB, Password, Username , CreatedAt, AccountStatus , LastLogin, tbl_Departments.deptName as Department
                from tbl_Users
                join tbl_Departments on tbl_Users.deptID = tbl_Departments.deptID
                WHERE UserID = ?;
                """;

        UserModel userModel = null;
        try {
            Connection connection = DatabaseConnectionHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userModel = new UserModel(
                        resultSet.getInt("UserID"), // Include this if your UserModel constructor expects it
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Email"),
                        resultSet.getString("Gender"),
                        resultSet.getString("Photo"),
                        resultSet.getString("Phone"),
                        resultSet.getString("DOB"),
                        resultSet.getString("Password"),
                        resultSet.getString("Username"),
                        resultSet.getString("CreatedAt"),
                        resultSet.getString("AccountStatus"),
                        resultSet.getString("Department"),
                        resultSet.getString("LastLogin")
                );

            }
            System.out.println(userModel);
            DatabaseConnectionHandler.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userModel;
    }

    /*
    public void updateUser(UserModel userModel) {
        String sql = "UPDATE tbl_Users SET FirstName = ?, LastName = ?, Email";
    }*/

    public void updateProfilePhoto(int userID, String photo, String lastModified) {
        String sql = "UPDATE tbl_Users SET Photo = ?, LastModified = ? WHERE UserID = ?";

        try {
            Connection connection = DatabaseConnectionHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, photo);
            preparedStatement.setString(2, lastModified);
            preparedStatement.setInt(3, userID);

            preparedStatement.executeUpdate();
            DatabaseConnectionHandler.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*     try {
        Connection connection = DatabaseConnectionHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, lastLogin);
        preparedStatement.setInt(2, userID);
        preparedStatement.executeUpdate();
        DatabaseConnectionHandler.closeConnection(connection);
    } catch (SQLException e) {
        e.printStackTrace();
    }*/
    public void updateUserLastLoginTime(int userID, String lastLogin) {
        String sql = "UPDATE tbl_Users SET LastLogin = ? WHERE UserID = ?";


        try(Connection connection = DatabaseConnectionHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, lastLogin);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateCurrentLoggedUserProfile(Integer userID, String FirstName, String LastName, String Email, String Phone, String Gender, String Password) {
        //first name, last name,email, phone,gender, password
        String sql = """
                UPDATE tbl_Users SET FirstName = ?, 
                LastName = ?, Email = ?, Phone = ? = Gender = ?, Password = ? 
                WHERE UserID = ?
                
                """;

        try{
            Connection connection = DatabaseConnectionHandler.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, FirstName);
            preparedStatement.setString(2, LastName);
            preparedStatement.setString(3, Email);
            preparedStatement.setString(4, Phone);
            preparedStatement.setString(5, Gender);
            preparedStatement.setString(6, Password);
            preparedStatement.setInt(7, userID);
            preparedStatement.executeUpdate();

            DatabaseConnectionHandler.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}






