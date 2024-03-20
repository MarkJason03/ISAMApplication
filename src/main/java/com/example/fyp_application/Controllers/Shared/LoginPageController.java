package com.example.fyp_application.Controllers.Shared;

import com.example.fyp_application.Controllers.Admin.DashboardControllers.ModifiedAdminDashboardController;
import com.example.fyp_application.Controllers.Client.DashboardControllers.ClientDashboardController;
import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Utils.DatabaseConnectionHandler;
import com.example.fyp_application.Utils.SharedButtonUtils;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
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


    //private final Object lock = new Object();
    private double x= 0 ;
    private double y= 0;



    public void checkDatabaseConnection() {

        // Check if the database is connected
        if (DatabaseConnectionHandler.isDbConnected(Objects.requireNonNull(DatabaseConnectionHandler.getConnection()))){
            dbStatusCheck_lbl.setText("Back-end Status: Database Connected");
        } else {
            // If the database is not connected, display an error message and exit the application
            System.out.println("Database Connection Failed");
            System.exit(0);
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
        SharedButtonUtils.exitApplication(exit_btn,
                AlertNotificationHandler.showConfirmationAlert("Exit Application?", "Do you want to exit this application?"));
    }


    @FXML
    private boolean isValidTextFields(){
        // Check if username and password fields are empty
        return !username_TF.getText().isEmpty() && !password_PF.getText().isEmpty();
    }

    @FXML
    private void loginFailedWarning(){
        AlertNotificationHandler.showErrorMessageAlert("Login Failed", "Invalid Username or Password");
        error_lbl.setText("");
    }

    @FXML
    private void expiredAccountWarning(){
        AlertNotificationHandler.showInformationMessageAlert("Account Expired", "Your account has expired. Please contact the administrator to renew your account.");
        username_TF.clear();
        password_PF.clear();
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

        Task<Boolean> loginValidationTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {

                for (int stepCounter = 0; stepCounter < 10; stepCounter++) {
                    updateProgress(stepCounter + 1, 10);
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

        progressBar.progressProperty().bind(loginValidationTask.progressProperty());

        loginValidationTask.setOnSucceeded(e -> {
            // Get the result of the task
            boolean isValidLogin = loginValidationTask.getValue();

            // Enable the login button and exit button - releasing the lock
            login_btn.setDisable(false);
            exit_btn.setDisable(false);

            // Remove the progress bar from the UI
            contentAP.getChildren().remove(progressBar); // Remove the progress bar from UI
            contentAP.getChildren().remove(tempLabel);

            // If the login is not valid, show an error message
            if(!isValidLogin) {
                expiredAccountWarning();
            }
        });

        loginValidationTask.setOnFailed(e -> {
            // Enable the login button and exit button - releasing the lock
            login_btn.setDisable(false);
            exit_btn.setDisable(false);

            // Remove the progress bar from the UI
            contentAP.getChildren().remove(progressBar); // Ensure progress bar is removed
            contentAP.getChildren().remove(tempLabel);

            // Print the exception stack trace
            e.getSource().getException().printStackTrace();
            expiredAccountWarning();
        });

        // No need to manually start a thread; JavaFX does this when executing the task
        new Thread(loginValidationTask).start();
    }

    private void handleSuccessfulLogin(UserModel userDetails) throws IOException {
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
                AlertNotificationHandler.showInformationMessageAlert("Login Successful", "Welcome Admin " + firstName + "!");
                openAdminView(userID, firstName, photoPath);
            }
            case "User" ->{
                // Logic for user
                AlertNotificationHandler.showInformationMessageAlert("Login Successful", "Welcome " + firstName + "!");
                openClientView(userID, firstName, photoPath);
            }
        }
    }

    public void openClientView(Integer userID, String name, String photoPath) throws IOException {

        //Store the current ID of the logged-in user - used for fetching data from the database within the application
        CurrentLoggedUserHandler.setCurrentUser(userID, name, photoPath);


        login_btn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(ViewConstants.CLIENT_DASHBOARD_VIEW)));
        Parent parent = loader.load(); // Load the FXML and get the root node


        ClientDashboardController controller = loader.getController(); // Get the controller instance

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
        stage.getIcons().add(new Image(getClass().getResourceAsStream(ViewConstants.APP_ICON)));

    }


    public void openAdminView(Integer userID, String name, String photoPath) throws IOException {


        //Store the current ID of the logged-in user - used for fetching data from the database within the application

        CurrentLoggedUserHandler.setCurrentAdmin(userID, name, photoPath);
        login_btn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(ViewConstants.ADMIN_DASHBOARD)));
        Parent parent = loader.load(); // Load the FXML and get the root node


        ModifiedAdminDashboardController controller = loader.getController(); // Get the controller instance

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
        stage.getIcons().add(new Image(getClass().getResourceAsStream(ViewConstants.APP_ICON)));


    }


    @FXML
    private void minimizeApplication() {
        //Minimize the application
        Stage stage = (Stage) login_btn.getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    private void closeApplication() {
/*
        Stage stage = (Stage) exit_btn.getScene().getWindow();

        if (AlertNotificationHandler.showConfirmationAlert("Exit Application", "Are you sure you want to exit?")) {
            stage.close();
        }*/

        SharedButtonUtils.exitApplication(exit_btn,
                AlertNotificationHandler.showConfirmationAlert("Exit Application?", "Do you want to exit this application?"));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //check if the database is connected
        checkDatabaseConnection();


        contentAP.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButtonAction();
            }
        });


/*        Thread accountUpdateThread = new Thread(() -> {
            synchronized (lock) {
                UserDAO.checkAndUpdateInactiveAccountStatus();
                UserDAO.checkAndUpdateExpiredAccountStatus();
            }
        });
        accountUpdateThread.start();
    }*/
    }
}

