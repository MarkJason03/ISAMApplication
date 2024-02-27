package com.example.fyp_application.Controllers;

import com.example.fyp_application.Controllers.Client.RevisedDBController;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertHandler;
import com.example.fyp_application.Utils.DatabaseConnectionHandler;
import com.example.fyp_application.Model.LoginModel;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Views.ViewHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private Label dbStatusCheck_lbl;



    private final AlertHandler ALERT_HANDLER = new AlertHandler();
    private double x= 0 ;
    private double y= 0;



    public void checkDatabaseConnection() {
/*
        if (LoginModel.isDbConnected()) {
            dbStatusCheck_lbl.setText("Back-end Status: Database Connected");
        } else {
            System.out.println("Database Connection Failed");
        }
*/

        if (DatabaseConnectionHandler.isDbConnected(Objects.requireNonNull(DatabaseConnectionHandler.getConnection()))){
            dbStatusCheck_lbl.setText("Back-end Status: Database Connected");
        } else {
            dbStatusCheck_lbl.setText("Back-end Status: Database Connection Failed");
        }
    }


    @FXML
    private void loginButtonAction() {
        if (isValidTextFields()) {
            handleLogin();
        } else {
            error_lbl.setText("Empty Username or Password");
        }
    }

    @FXML
    private void exitApplication() {

        if (ALERT_HANDLER.showConfirmationAlert("Exit Confirmation","Are you sure you want to exit?")){
            System.exit(0);
        }
    }


    public boolean isValidTextFields(){
        // Check if username and password fields are empty
        return !username_TF.getText().isEmpty() && !password_PF.getText().isEmpty();
    }

    public void loginFailed(){
        ALERT_HANDLER.showErrorMessageAlert("Login Failed", "Invalid Username or Password");
        error_lbl.setText("");
    }


    public void handleLogin(){
        //otherwise , check if the username and password are valid and exist in the database
        String sql = """
                SELECT *
                from tbl_Users
                JOIN tbl_userRoles ON tbl_Users.userRoleID = tbl_userRoles.userRoleID
                Where tbl_Users.Username = ? AND tbl_Users.Password = ?;
                """;

        Connection connection = DatabaseConnectionHandler.getConnection();
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
            DatabaseConnectionHandler.closeConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSuccessfulLogin(ResultSet resultSet) throws SQLException, IOException {

/*        UserModel user = new UserModel(
                resultSet.getInt("UserID"),
                resultSet.getInt("userRoleID"),
                resultSet.getInt("deptID"),
                resultSet.getString("FirstName"),
                resultSet.getString("LastName"),
                resultSet.getString("Gender"),
                resultSet.getString("DOB"),
                resultSet.getString("Email"),
                resultSet.getString("Username"),
                resultSet.getString("Password"),
                resultSet.getString("Phone"),
                resultSet.getString("AccountStatus"),
                resultSet.getString("Photo"),
                resultSet.getString("CreatedAt"),
                resultSet.getString("ExpiresOn")
        );*/
        // Get the first name and role of the user
        String first_name = resultSet.getString("FirstName");
        String role = resultSet.getString("userRoleName"); // Assuming 'userRoleName' holds the role name
        Integer userID = Integer.valueOf(resultSet.getString("UserID"));
        String photoPath = resultSet.getString("Photo");
        String name = resultSet.getString("FirstName");

        handleRoleBasedLogin(userID, name, photoPath , first_name, role);

    }

    private void handleRoleBasedLogin(Integer userID, String name, String photoPath, String firstName, String role) throws IOException {
        // Logic for handling role based login - Current defined roles are "Admin" and "User"
        // Perhaps hashmap would suit this better on a larger scale
        switch (role) {
            case "Admin" ->{
                // Logic for admin
                ALERT_HANDLER.showInformationMessageAlert("Login Successful", "Welcome Admin " + firstName + "!");

                openAdminView();
            }


            case "User" ->{
                // Logic for user
                ALERT_HANDLER.showInformationMessageAlert("Login Successful", "Welcome " + firstName + "!");
                openClientView(userID, name, photoPath);
            }


        }

    }

    public void openClientView(Integer userID, String name, String photoPath) throws IOException {

        //Store the current ID of the logged-in user - used for fetching data from the database within the application
        CurrentLoggedUserHandler.setCurrentUser(userID, name, photoPath);

        login_btn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(ViewHandler.CLIENT_DASHBOARD_VIEW)));
        Parent parent = loader.load(); // Load the FXML and get the root node

        RevisedDBController controller = loader.getController(); // Get the controller instance
        //controller.loadCurrentUserInfo(userID,name,photoPath ); // Pass the user data to the controller

        Stage stage = new Stage();
        Scene scene = new Scene(parent);

        // Your existing setup for draggable window
        parent.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        parent.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();


    }


    public void openAdminView() throws IOException {


        login_btn.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/AdminView/AdminDashboard.fxml")));
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
