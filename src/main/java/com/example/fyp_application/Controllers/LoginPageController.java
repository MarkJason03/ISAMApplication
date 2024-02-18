package com.example.fyp_application.Controllers;

import com.example.fyp_application.Model.DatabaseHandler;
import com.example.fyp_application.Model.LoginModel;
import com.example.fyp_application.Model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    private Button login_btn;

    @FXML
    private Button exit_btn;

    @FXML
    private TextField username_TF;

    @FXML
    private PasswordField password_PF;

    @FXML
    private Label error_lbl;


    @FXML
    private ListView<UserModel> userListView;

    @FXML
    private Label dbStatusCheck_lbl;

    private final LoginModel loginModel = new LoginModel();

    private final AlertHandlerController alertHandlerController = new AlertHandlerController();
    private double x= 0 ;
    private double y= 0;



    public void checkDatabaseConnection() {
        if (LoginModel.isDbConnected()) {
            dbStatusCheck_lbl.setText("Back-end Status: Database Connected");
        } else {
            System.out.println("Database Connection Failed");
        }

    }


    public void loginButtonAction() {
        if (isValidInput()) {
            handleLogin();
        } else {
            error_lbl.setText("Empty Username or Password");
        }
    }
        // Check if username and password fields are empty
/*        if (username_TF.getText().isEmpty() ||
        password_PF.getText().isEmpty()){
            //set placeholder text as this error message
            error_lbl.setText("Please enter username and password");
        }

        else{
            //otherwise , check if the username and password are valid and exist in the database
            String sql = "SELECT tbl_Users.UserID, tbl_Users.Username, tbl_Users.Password, tbl_Users.FirstName, tbl_Users.userRoleID, tbl_userRoles.userRoleName, tbl_userRoles.userRoleID AS userRoleID\n" +
                    "FROM tbl_Users\n" +
                    "JOIN tbl_userRoles ON tbl_Users.userRoleID = tbl_userRoles.userRoleID\n" +
                    "WHERE tbl_Users.Username = ? AND tbl_Users.Password = ?;\n";

            Connection connection = DatabaseHandler.getConnection();
            try {
                assert connection != null;
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username_TF.getText());
                preparedStatement.setString(2, password_PF.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String first_name = resultSet.getString("FirstName");
                    String role =  resultSet.getString("userRoleName");
                    System.out.println(role);
                    alertHandlerController.showAlert("Login Successful", "Welcome " + first_name + "!");
                } else {
                    alertHandlerController.showError("Login Failed", "Invalid Username or Password");
                    error_lbl.setText("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }*/
/*            try(Connection connection = SqliteConnection.getConnection()){
                PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM tbl_Users");
                ResultSet resultSet = prepareStatement.executeQuery();

                while (resultSet.next()){
                    UserModel userModel = new UserModel();
                    userModel.getUsername(resultSet.getString("username"));
                    userModel.setPassword(resultSet.getString("password"));
                    userListView.getItems().add(userModel);
                }*/

  /*          try(Connection connection = DatabaseConnection.getConnection()) {
                UserModel userModel = new UserModel();
                userModel.setUsername(username_TF.getText());
                userModel.setPassword(password_PF.getText());

                if (userModel.login(connection)){
                    login_btn.getScene().getWindow().hide();
                    Parent parent = FXMLLoader.load(getClass().getResource("/FXML/AdminSide/AdminDashboard.fxml"));
                    Stage stage  = new Stage();
                    Scene scene = new Scene(parent);

                    parent.setOnMousePressed((MouseEvent event) ->{
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    parent.setOnMouseDragged((MouseEvent event) ->{
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);

                    stage.show();
                }else {
                    error_lbl.setText("Invalid username or password");
                }*/

/*        login_btn.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/AdminSide/AdminDashboard.fxml"));
        Stage stage  = new Stage();
        Scene scene = new Scene(parent);

        parent.setOnMousePressed((MouseEvent event) ->{
             x = event.getSceneX();
             y = event.getSceneY();
        });

        parent.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        stage.show();*/

    public void exitButtonAction() {
        System.out.println("Exit Button Clicked");

        if (alertHandlerController.confirmationDialogueBox("Are you sure you want to exit?")){
            System.exit(0);
        }
    }


    public boolean isValidInput(){
        // Check if username and password fields are empty
        return !username_TF.getText().isEmpty() && !password_PF.getText().isEmpty();
    }

    public void loginFailed(){
        alertHandlerController.showError("Login Failed", "Invalid Username or Password");
        error_lbl.setText("");
    }


    public void handleLogin(){
        //otherwise , check if the username and password are valid and exist in the database
        String sql = "SELECT tbl_Users.UserID, tbl_Users.Username, tbl_Users.Password, tbl_Users.FirstName, tbl_Users.userRoleID, tbl_userRoles.userRoleName, tbl_userRoles.userRoleID AS userRoleID\n" +
                "FROM tbl_Users\n" +
                "JOIN tbl_userRoles ON tbl_Users.userRoleID = tbl_userRoles.userRoleID\n" +
                "WHERE tbl_Users.Username = ? AND tbl_Users.Password = ?;\n";

        Connection connection = DatabaseHandler.getConnection();
        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username_TF.getText());
            preparedStatement.setString(2, password_PF.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                handleSuccessfulLogin(resultSet);
            } else {
                loginFailed();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSuccessfulLogin(ResultSet resultSet) throws SQLException, IOException {
        // Get the first name and role of the user
        String first_name = resultSet.getString("FirstName");
        String role = resultSet.getString("userRoleName"); // Assuming 'userRoleName' holds the role name
        handleRoleBasedLogin(first_name, role);
    }

    private void handleRoleBasedLogin(String firstName, String role) throws IOException {
        // Logic for handling role based login - Current defined roles are "Admin" and "User"
        // Perhaps hashmap would suit this better on a larger scale
        switch (role) {
            case "Admin" ->{
                // Logic for admin
                alertHandlerController.showAlert("Login Successful", "Welcome Admin " + firstName + "!");
/*                login_btn.getScene().getWindow().hide();
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/AdminSide/AdminDashboard.fxml")));
                Stage stage  = new Stage();
                Scene scene = new Scene(parent);

                parent.setOnMousePressed((MouseEvent event) ->{
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                parent.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();*/
                openAdminView();
            }


            case "User" ->{
                // Logic for user
                alertHandlerController.showAlert("Login Successful", "Welcome " + firstName + "!");
                openClientView();
            }


        }

    }




    public void openAdminView() throws IOException {
        login_btn.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/AdminSide/AdminDashboard.fxml")));
        Stage stage  = new Stage();
        Scene scene = new Scene(parent);

        parent.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        parent.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();


    }


    public void openClientView() throws IOException {
        login_btn.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/ClientSide/ClientDashboard.fxml")));
        Stage stage  = new Stage();
        Scene scene = new Scene(parent);

        parent.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        parent.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //check if the database is connected
        checkDatabaseConnection();
    }
}
