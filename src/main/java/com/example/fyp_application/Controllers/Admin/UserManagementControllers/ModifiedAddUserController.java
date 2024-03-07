package com.example.fyp_application.Controllers.Admin.UserManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.*;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.application.Platform;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ModifiedAddUserController implements Initializable {

    @FXML
    private ChoiceBox<UserRoleModel> accountRole_CB;

    @FXML
    private ChoiceBox<String> accountStatus_CB;

    @FXML
    private Button cancel_btn;


    @FXML
    private Button createUser_btn;

    @FXML
    private DatePicker createdOn_DP;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private DatePicker dob_DP;

    @FXML
    private DatePicker expiresAt_DP;

    @FXML
    private PasswordField password_TF;

    @FXML
    private Label passwordChecker_lbl1;

    @FXML
    private Button randomPassword_btn;

    @FXML
    private Button sendEmail_btn;

    @FXML
    private ChoiceBox<DepartmentModel> userDept_CB;

    @FXML
    private TextField userEmail_TF;

    @FXML
    private TextField userFirstName_TF;

    @FXML
    private ChoiceBox<String> userGender_TF;

    @FXML
    private TextField userLastName_TF;

    @FXML
    private TextField userName_TF;

    @FXML
    private TextField userWorkPhone_TF;

    AlertNotificationHandler ALERT_HANDLER = new AlertNotificationHandler();
    UserRoleDAO USER_ROLE_DAO = new UserRoleDAO();

    DepartmentDAO DEPARTMENT_DAO = new DepartmentDAO();
    private static final String DEFAULT_EMAIL = "projecthandler51@gmail.com";

    //Default photo for the user profile
    private static final String DEFAULT_USER_PLACEHOLDER_PHOTO = "/Assets/defaultUser.png";

    @FXML
    private void cancelCreateUserAction(){
        // Close the window
        cancel_btn.getScene().getWindow().hide();
    }

/*
    private void addUser(){
        // check if the phone number is valid

        if (userWorkPhone_TF.getText().length() < 11) {
            ALERT_HANDLER.showErrorMessageAlert("Invalid Phone Number", "The phone number must be 11 digits long");
        }

        if( isEmptyFields()){
            ALERT_HANDLER.showErrorMessageAlert("Invalid Entry", "Please fill in all fields and select a valid date");
        } else {


            UserDAO.addUser(
                    accountRole_CB.getValue().getUserRoleID(),
                    userDept_CB.getValue().getDeptID(),
                    userFirstName_TF.getText(),
                    userLastName_TF.getText(),
                    userGender_TF.getValue(),
                    DateTimeHandler.setSQLiteDateFormat(dob_DP.getValue()),
                    userEmail_TF.getText(),
                    userName_TF.getText(),
                    PasswordHashHandler.hashPassword(password_TF.getText()),
                    userWorkPhone_TF.getText(),
                    accountStatus_CB.getValue(),
                    DEFAULT_PHOTO,
                    DateTimeHandler.setSQLiteDateFormat(createdOn_DP.getValue()),
                    DateTimeHandler.setSQLiteDateFormat(expiresAt_DP.getValue())
            );
            ALERT_HANDLER.showInformationMessageAlert("Success", "User added successfully");

            Platform.runLater(() -> {
                try {
                    sendAccountDetailEmail();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Platform.runLater(cancel_btn.getScene().getWindow()::hide);
        }
    }*/

    @FXML
    private void addUser() {
        // check if the phone number is valid
        if (userWorkPhone_TF.getText().length() < 11) {
            ALERT_HANDLER.showErrorMessageAlert("Invalid Phone Number", "The phone number must be 11 digits long");
            return; // Stop execution if validation fails
        }

        // check if any field is empty
        if (isEmptyFields()) {
            ALERT_HANDLER.showErrorMessageAlert("Invalid Entry", "Please fill in all fields and select a valid date");
        } else {
            UserDAO.addUser(
                    accountRole_CB.getValue().getUserRoleID(),
                    userDept_CB.getValue().getDeptID(),
                    userFirstName_TF.getText(),
                    userLastName_TF.getText(),
                    userGender_TF.getValue(),
                    DateTimeHandler.setSQLiteDateFormat(dob_DP.getValue()),
                    userEmail_TF.getText(),
                    userName_TF.getText(),
                    PasswordHashHandler.hashPassword(password_TF.getText()),
                    userWorkPhone_TF.getText(),
                    accountStatus_CB.getValue(),
                    DEFAULT_USER_PLACEHOLDER_PHOTO,
                    DateTimeHandler.setSQLiteDateFormat(createdOn_DP.getValue()),
                    DateTimeHandler.setSQLiteDateFormat(expiresAt_DP.getValue())
            );
            ALERT_HANDLER.showInformationMessageAlert("Success", "User added successfully");

            Task<Void> emailTask = getEmailTask();

            new Thread(emailTask).start(); // Run the task in its own thread

            Platform.runLater(cancel_btn.getScene().getWindow()::hide);
        }
    }

    private Task<Void> getEmailTask() {
        Task<Void> emailTask = new Task<>() {
            @Override
            protected Void call() {
                try {
                    sendAccountDetailEmail();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle exceptions, potentially update the UI with the failure.
                }
                return null;
            }
        };

        emailTask.setOnSucceeded(event -> {
            // UI update after email sent can go here, executed on JavaFX Application Thread
            //ALERT_HANDLER.showInformationMessageAlert("Email sent", "Account details emailed successfully");
            System.out.println("Email sent");
        });

        emailTask.setOnFailed(event -> {
            // UI update after email sending failure can go here
            System.out.println("Email failed");
            //ALERT_HANDLER.showErrorMessageAlert("Email failed", "Failed to send account details");
        });
        return emailTask;
    }


    @FXML
    private boolean isEmptyFields(){
        return  userName_TF.getText().isEmpty()
                || userFirstName_TF.getText().isEmpty()
                || userLastName_TF.getText().isEmpty()
                || userEmail_TF.getText().isEmpty()
                || userWorkPhone_TF.getText().isEmpty()
                || userGender_TF.getValue().isEmpty()
                || userDept_CB.getValue().toString().isEmpty()
                || accountRole_CB.getValue().toString().isEmpty()
                || accountStatus_CB.getValue().isEmpty()
                || password_TF.getText().isEmpty();
    }


    @FXML
    private void resetForm(){
        // Clear all the fields
        userName_TF.clear();
        userFirstName_TF.clear();
        userLastName_TF.clear();
        userEmail_TF.clear();
        userWorkPhone_TF.clear();
        userGender_TF.setValue(null);
        userDept_CB.setValue(null);
        accountRole_CB.setValue(null);
        accountStatus_CB.setValue(null);
        password_TF.clear();
        createdOn_DP.setValue(LocalDate.now());
        expiresAt_DP.setValue(null);
        dob_DP.setValue(null);
    }


    @FXML
    private void generateRandomPassword(){
        String randomPassword = InformationGeneratorHandler.generatePassword(12);
        password_TF.setText(randomPassword);

    }


    @FXML
    private void sendAccountDetailEmail() throws Exception {

        if (userName_TF.getText().isEmpty() || password_TF.getText().isEmpty()) {
            ALERT_HANDLER.showErrorMessageAlert("Empty Fields", "Cannot send details without a username and password");
        } else {
            GMailHandler gMailHandler = new GMailHandler();
            gMailHandler.sendEmailTo(userEmail_TF.getText(),"User Account Details",
                    gMailHandler.generateAccountCreationEmailBody(
                            userFirstName_TF.getText(),
                            userName_TF.getText(),
                            password_TF.getText()));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createdOn_DP.setValue(LocalDate.now());
        userEmail_TF.setText(DEFAULT_EMAIL);
        userGender_TF.setItems(FXCollections.observableArrayList("Male","Female"));
        accountStatus_CB.setValue("Active");
        DateTimeHandler.dateTimeUpdates(dateTimeHolder);

        List<DepartmentModel>departmentList = DEPARTMENT_DAO.getAllDepartments();
        userDept_CB.setItems(FXCollections.observableArrayList(departmentList));

        List<UserRoleModel> userRoleList = USER_ROLE_DAO.getAllRoles();
        accountRole_CB.setItems(FXCollections.observableArrayList(userRoleList));



        userFirstName_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2 && userLastName_TF.getText().length() >= 2) {
                userName_TF.setText(InformationGeneratorHandler.generateUsername(userFirstName_TF.getText(), userLastName_TF.getText()));
            } else {
                userName_TF.setText("");
            }
        });


        userLastName_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >=2 && userFirstName_TF.getText().length() >= 2) {
                userName_TF.setText(InformationGeneratorHandler.generateUsername(userFirstName_TF.getText(), userLastName_TF.getText()));

            } else {
                userName_TF.setText("");
            }
        });


        userWorkPhone_TF.textProperty().addListener((observable, oldValue, newInput) -> {

            // Remove spaces from the new value and set it to the TextField.
            String numberOnlyValue = newInput.replaceAll("\\D", "");

            // check the length of the phone number
            if(numberOnlyValue.length() > 11){
                // stop the user from entering more than 11 characters
                userWorkPhone_TF.setText(numberOnlyValue.substring(0, 11));


            } else if (!numberOnlyValue.equals(newInput)) {
                //if the new value is not a digit, replace it with the sanitized value
                userWorkPhone_TF.setText(numberOnlyValue);
            }

            if (numberOnlyValue.length() == 11 ){
                System.out.println("Valid phone number");
            }
        });

/*
        userName_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (UserDAO.isUsernameTaken(newValue)) {

                userName_TF.setStyle("-fx-border-color: red");
            } else {
                userName_TF.setStyle("-fx-border-color: green");
            }
        });
*/


        PauseTransition pause = new PauseTransition(Duration.seconds(1)); //

        userName_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            // Stop any previously running pause transition
            pause.stop();

            // Set the action to run after the specified debounce time
            pause.setOnFinished(event -> {
                new Thread(() -> {
                    final boolean usernameTaken = UserDAO.isUsernameTaken(newValue);
                    Platform.runLater(() -> {
                        if (usernameTaken) {
                            userName_TF.setStyle("-fx-border-color: red;");
                        } else {
                            userName_TF.setStyle("-fx-border-color: green;");
                        }
                    });
                }).start();
            });

            // Reset and start the pause transition
            pause.playFromStart();
        });


        dob_DP.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate currentDate = LocalDate.now();
            LocalDate eighteenYearsAgo = currentDate.minusYears(18);

            if (newValue != null && newValue.isAfter(eighteenYearsAgo)) {
                System.out.println("Invalid date: The date cannot be in the future.");
                ALERT_HANDLER.showInformationMessageAlert("Invalid Employee DOB", "The employee must be at least 18 years old.");
                dob_DP.setValue(null);  // Revert to the old value if new value is invalid
                dob_DP.setStyle("-fx-border-color: red");
            } else {
                // Handle valid date selection
                dob_DP.setStyle("-fx-border-color: green");
                System.out.println("Valid date selected: " + newValue);
            }
        });


        expiresAt_DP.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                System.out.println("Invalid date: The date cannot be in the past.");
                ALERT_HANDLER.showInformationMessageAlert("Invalid Date", "The date cannot be in the past.");
                expiresAt_DP.setValue(null);  // Revert to the old value if new value is invalid
                expiresAt_DP.setStyle("-fx-border-color: red");

            } else {
                // Handle valid date selection
                expiresAt_DP.setStyle("-fx-border-color: green");
                System.out.println("Valid date selected: " + newValue);
            }
        });


    }
}
