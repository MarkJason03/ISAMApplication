package com.example.fyp_application.Controllers;

import com.example.fyp_application.Controllers.Admin.ModifiedAdminDashboardController;
import com.example.fyp_application.Controllers.Client.RevisedDBController;
import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Utils.DatabaseConnectionHandler;
import com.example.fyp_application.Utils.DateTimeHandler;
import com.example.fyp_application.Views.ViewHandler;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    private AnchorPane contentAP;
    @FXML
    private Button login_btn;

    @FXML
    private VBox loginVbox;
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


    private final ProgressBar progressBar = new ProgressBar();

    private final UserDAO USER_DAO = new UserDAO();

    private final AlertNotificationHandler ALERT_HANDLER = new AlertNotificationHandler();
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

    public void handleLogin() {

        Label tempLabel = new Label("Validating Credentials...");

        progressBar.setPrefWidth(200);
        progressBar.setPrefHeight(20);
        progressBar.setLayoutX(715);
        progressBar.setLayoutY(442);

        tempLabel.setLayoutX(720);
        tempLabel.setLayoutY(442);

        contentAP.getChildren().add(progressBar);
        contentAP.getChildren().add(tempLabel);

        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {



                for (int counter = 0; counter < 10; counter++) {
                    updateProgress(counter + 1, 10);
                    Thread.sleep(100); // Simulate some work being done

                    login_btn.setDisable(true);
                    exit_btn.setDisable(true);
                }

                // Perform the actual validation on the background thread
                boolean isValidLogin = USER_DAO.validateLoginCredentials(username_TF.getText(), password_PF.getText());

                if (isValidLogin) {

                    //
                    UserModel userDetails = USER_DAO.cacheUserLoginID(username_TF.getText()); //
                    if (userDetails != null) {
                        Platform.runLater(() -> {
                            try {
                                handleSuccessfulLogin(userDetails); //
                            } catch (Exception e) {
                                e.printStackTrace(); // Handle exceptions properly
                            }
                        });
                    }
                }
                return isValidLogin;
            }
        };

        progressBar.progressProperty().bind(task.progressProperty());
        task.messageProperty().addListener((obs, oldMessage, newMessage) -> {
            // Update UI with the task's current message, e.g., updating a status label
            // statusLabel.setText(newMessage); // Assuming you have a status label
        });

        task.setOnSucceeded(e -> {
            boolean result = task.getValue();
            login_btn.setDisable(false);
            exit_btn.setDisable(false);
            contentAP.getChildren().remove(progressBar); // Remove the progress bar from UI
            contentAP.getChildren().remove(tempLabel);
            if (!result) {
                loginFailed();
            }
        });

        task.setOnFailed(e -> {
            login_btn.setDisable(false);
            exit_btn.setDisable(false);
            contentAP.getChildren().remove(progressBar); // Ensure progress bar is removed
            contentAP.getChildren().remove(tempLabel);
            e.getSource().getException().printStackTrace();
            loginFailed();
        });

        // No need to manually start a thread; JavaFX does this when executing the task
        new Thread(task).start();
    }

    private void handleSuccessfulLogin(UserModel userDetails) throws SQLException, IOException {

        // Get the first name and role of the user
            String first_name = userDetails.getFirstName();
            String role = userDetails.getRoleName(); // Assuming 'userRoleName' holds the role name
            Integer userID = userDetails.getUserID();
            String photoPath = userDetails.getPhoto();



            handleRoleBasedLogin(userID, first_name, photoPath, role);


    }

    private void handleRoleBasedLogin(Integer userID, String firstName, String photoPath, String role) throws IOException {
        // Logic for handling role based login - Current defined roles are "Admin" and "User"
        // Perhaps hashmap would suit this better on a larger scale
        switch (role) {
            case "Admin" ->{
                // Logic for admin
                ALERT_HANDLER.showInformationMessageAlert("Login Successful", "Welcome Admin " + firstName + "!");
                openAdminView(userID, firstName, photoPath);
            }
            case "User" ->{
                // Logic for user
                ALERT_HANDLER.showInformationMessageAlert("Login Successful", "Welcome " + firstName + "!");
                openClientView(userID, firstName, photoPath);
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
        //controller.setLastLoginTime(userID, lastLoginTime); // Update the last login time

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


    public void openAdminView(Integer userID, String name, String photoPath) throws IOException {


        //Store the current ID of the logged-in user - used for fetching data from the database within the application
        CurrentLoggedUserHandler.setCurrentUser(userID, name, photoPath);

        login_btn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(ViewHandler.ADMIN_DASHBOARD)));
        Parent parent = loader.load(); // Load the FXML and get the root node


        ModifiedAdminDashboardController controller = loader.getController(); // Get the controller instance
        //controller.setLastLoginTime(userID, lastLoginTime); // Update the last login time

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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //check if the database is connected
        checkDatabaseConnection();
    }
}
