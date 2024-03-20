package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;
import com.example.fyp_application.Utils.PasswordHashHandler;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.sql.*;

public class UserDAO {


    public static boolean isUsernameTaken(String Username) {

        String sql = "SELECT tbl_Users.Username from tbl_Users where Username = ?";
        boolean isUsernameTaken = false;

        try {
            Connection connection = DatabaseConnectionHandler.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isUsernameTaken = true;
                System.out.println("Username is taken");
            }
            System.out.println("Username is not taken");
            DatabaseConnectionHandler.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return isUsernameTaken;
    }


    public boolean validateLoginCredentials(String username, String password) {
        String sql = "SELECT AccountStatus, Password FROM tbl_Users WHERE Username = ?";



        try (Connection connection = DatabaseConnectionHandler.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("Password");
                    boolean isValidAccount = PasswordHashHandler.verifyPassword(hashedPassword, password);

                    return isValidAccount && validateAccountStatus(resultSet.getString("AccountStatus"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private boolean validateAccountStatus(String accountStatus) {
        return accountStatus.equals("Active");
    }


    public UserModel cacheUserLoginID(String username) {
        String sql = """
                SELECT UserID, FirstName, Photo, tbl_userRoles.userRoleName
                        FROM tbl_Users\s
                        JOIN tbl_userRoles ON tbl_Users.userRoleID = tbl_userRoles.userRoleID\s
                        WHERE tbl_Users.Username = ?
                     """;

        try (Connection connection = DatabaseConnectionHandler.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new UserModel(resultSet.getInt("UserID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("userRoleName"),
                            resultSet.getString("Photo"));
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return null;
    }


    public static ObservableList<UserModel> getAllUsers()  {
        ObservableList<UserModel> arrayList = FXCollections.observableArrayList();

        String sql = """
                select UserID, tbl_Users.deptID, tbl_Users.userRoleID, FirstName, LastName, Gender, DOB, Email, Photo, Username, Phone, AccountStatus, CreatedAt, ExpiresOn,
                LastLogin, tbl_userRoles.userRoleName as Role, tbl_Departments.deptName as departmentName
                from tbl_Users
                join tbl_userRoles on tbl_Users.userRoleID = tbl_userRoles.userRoleID
                join tbl_Departments on tbl_Users.deptID = tbl_Departments.deptID;
                """;

        UserModel userModel;

        try (Connection connect = DatabaseConnectionHandler.getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    userModel = new UserModel(
                            resultSet.getInt("UserID"),
                            resultSet.getInt("userRoleID"),
                            resultSet.getInt("deptID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Gender"),
                            resultSet.getString("DOB"),
                            resultSet.getString("Email"),
                            resultSet.getString("Photo"),
                            resultSet.getString("Username"),
                            resultSet.getString("Phone"),
                            resultSet.getString("AccountStatus"),
                            resultSet.getString("CreatedAt"),
                            resultSet.getString("ExpiresOn"),
                            resultSet.getString("LastLogin"),
                            resultSet.getString("Role"),
                            resultSet.getString("departmentName"));

                    arrayList.add(userModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


    public static void addUser(int userRoleID, int deptID, String firstName, String lastName, String gender, String dob, String email, String username, String password, String phone, String accountStatus, String photo, String createdAt, String expiresOn) {

        String sql = """
                INSERT INTO tbl_Users (
                userRoleID,
                deptID,
                FirstName,
                LastName,
                Gender,
                DOB,
                Email,
                Username,
                Password,
                Phone,
                AccountStatus,
                Photo,
                CreatedAt,
                ExpiresOn) VALUES
                (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                
                """;

        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userRoleID);
                preparedStatement.setInt(2, deptID);
                preparedStatement.setString(3, firstName);
                preparedStatement.setString(4, lastName);
                preparedStatement.setString(5,gender);
                preparedStatement.setString(6, dob);
                preparedStatement.setString(7, email);
                preparedStatement.setString(8, username);
                preparedStatement.setString(9, password);
                preparedStatement.setString(10, phone);
                preparedStatement.setString(11, accountStatus);
                preparedStatement.setString(12, photo);
                preparedStatement.setString(13, createdAt);
                preparedStatement.setString(14, expiresOn);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e){
                e.printStackTrace();
        }

    }




    public void deleteUser(int userID) {
        String sql = "DELETE FROM tbl_Users WHERE UserID = ?";

        try(Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(
            );
        }
/*        try {
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
        */
    }

    public static int countActiveUsers() {

        int count = 0;

        String sql = "select count (UserID)\n" + "from tbl_Users\n" + "where AccountStatus = \"Active\"\n ";

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



    public static int countExpiredUsers(){
        int count = 0;

        String sql = "Select Count (UserID) FROM tbl_Users Where AccountStatus='Expired'; ";

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
    public static int countInactiveUsers() {

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


    public static void checkAndUpdateExpiredAccountStatus(){

        String sql = """
                UPDATE tbl_Users
                SET AccountStatus = 'Expired'
                WHERE ExpiresOn < date('now');
                """;


        try( Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.executeUpdate();
                System.out.println("\nPrepared Statement Executed");
                DatabaseConnectionHandler.closeConnection(connection);
                System.out.println("Expired Account Status Updated\n");
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }

    }

    public static void checkAndUpdateInactiveAccountStatus(){


        // Update the account status to "Inactive" if the user has not logged in for 7 days
        String sql = """
                UPDATE tbl_Users
                SET AccountStatus = 'Inactive'
                WHERE LastLogin IS NULL OR LastLogin < date('now', '-7 days');
                """;


        try( Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.executeUpdate();
                System.out.println("\nPrepared Statement Executed");
                DatabaseConnectionHandler.closeConnection(connection);
                System.out.println("Inactive Account Status Updated\n");
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }

    }



    public static UserModel loadCurrentLoggedUser(Integer userID) throws SQLException {
        String sql = """
                SELECT UserID, FirstName , LastName , Email, Gender,Photo, Phone, DOB, Username , CreatedAt, AccountStatus , LastLogin, tbl_Departments.deptName as Department
                from tbl_Users
                join tbl_Departments on tbl_Users.deptID = tbl_Departments.deptID
                WHERE UserID = ?;
                """;

        UserModel userModel = null;

        try (Connection connection = DatabaseConnectionHandler.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userModel = new UserModel(resultSet.getInt("UserID"), // Include this if your UserModel constructor expects it
                            resultSet.getString("FirstName"), resultSet.getString("LastName"), resultSet.getString("Email"), resultSet.getString("Gender"), resultSet.getString("Photo"), resultSet.getString("Phone"), resultSet.getString("DOB"), resultSet.getString("Username"), resultSet.getString("CreatedAt"), resultSet.getString("AccountStatus"), resultSet.getString("Department"), resultSet.getString("LastLogin"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userModel;
    }


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

    public static void updateUserAccountStatusAndLastLoginTime(int userID, String lastLogin) {
        String status = "Active";
        String sql = "UPDATE tbl_Users SET LastLogin = ?, AccountStatus = ? WHERE UserID = ? ";
        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, lastLogin);
                preparedStatement.setString(2, status);
                preparedStatement.setInt(3, userID);
                preparedStatement.executeUpdate();
                System.out.println("Last Login Time Updated" + lastLogin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUserPassword(int userID, String password) {

        String sql = "UPDATE tbl_Users SET Password = ? WHERE UserID = ?";
        try (Connection connection = DatabaseConnectionHandler.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void updateCurrentLoggedUserProfile(int userID, String firstName, String lastName, String email, String phone, String gender) {
        String sql = "UPDATE tbl_Users SET FirstName = ?, LastName = ?, Email = ?, Phone = ?, Gender = ? WHERE UserID = ?";

        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, gender);
                preparedStatement.setInt(6, userID);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void updateUserProfile(int userID,
                                         int userRoleID,
                                         int deptID,
                                         String firstName,
                                         String lastName,
                                         String gender,
                                         String phone,
                                         String email,
                                         String accountStatus,
                                         String expiresOn

    ) throws SQLException {


        String sql = """
                
                UPDATE tbl_Users Set
                userRoleID = ?,
                deptID = ?,
                FirstName = ?,
                LastName = ?,
                Gender= ?,
                Phone = ?,
                Email = ?,
                AccountStatus = ?,
                ExpiresOn = ?
                WHERE UserID = ?
                
                """;

        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userRoleID);
                preparedStatement.setInt(2, deptID);
                preparedStatement.setString(3, firstName);
                preparedStatement.setString(4, lastName);
                preparedStatement.setString(5,gender);
                preparedStatement.setString(6, phone);
                preparedStatement.setString(7, email);
                preparedStatement.setString(8, accountStatus);
                preparedStatement.setString(9, expiresOn);
                preparedStatement.setInt(10, userID);
                preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            }
        }
    }
}
