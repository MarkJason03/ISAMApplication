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
    private ChoiceBox<String> userGender_CB;

    @FXML
    private TextField userLastName_TF;

    @FXML
    private TextField userName_TF;

    @FXML
    private TextField userWorkPhone_TF;

    UserRoleDAO USER_ROLE_DAO = new UserRoleDAO();

    DepartmentDAO DEPARTMENT_DAO = new DepartmentDAO();
    private static final String DEFAULT_EMAIL = "projecthandler51@gmail.com";

    //Default photo for the user profile
    private static final String DEFAULT_USER_PLACEHOLDER_PHOTO = "/Assets/defaultUser.png";


    // Get the list of departments and user roles
    private final List<DepartmentModel>departmentList = DEPARTMENT_DAO.getAllDepartments();

    // Get the list of user roles
    private final List<UserRoleModel> userRoleList = USER_ROLE_DAO.getAllRoles();
    @FXML
    private void cancelCreateUserAction(){
        // Close the window
        cancel_btn.getScene().getWindow().hide();
    }

    @FXML
    private void addUser() {
        // check if any field is empty
        if (isEmptyFields()) {
            AlertNotificationUtils.showErrorMessageAlert("Invalid Entry", "Please fill in all fields and select a valid date");
            return; // Stop execution if validation fails
        }


        // check if the phone number is valid

        if (userWorkPhone_TF.getText().length() < 11) {
            AlertNotificationUtils.showErrorMessageAlert("Invalid Phone Number", "The phone number must be 11 digits long");
            return; // Stop execution if validation fails
        } else {
            UserDAO.addUser(
                    accountRole_CB.getValue().getUserRoleID(),
                    userDept_CB.getValue().getDeptID(),
                    userFirstName_TF.getText(),
                    userLastName_TF.getText(),
                    userGender_CB.getValue(),
                    DateTimeUtils.setYearMonthDayFormat(dob_DP.getValue()),
                    userEmail_TF.getText(),
                    userName_TF.getText(),
                    PasswordHashingUtils.hashPassword(password_TF.getText()),
                    userWorkPhone_TF.getText(),
                    accountStatus_CB.getValue(),
                    DEFAULT_USER_PLACEHOLDER_PHOTO,
                    DateTimeUtils.setYearMonthDayFormat(createdOn_DP.getValue()),
                    DateTimeUtils.setYearMonthDayFormat(expiresAt_DP.getValue())
            );
            AlertNotificationUtils.showInformationMessageAlert("Success", "User added successfully");

            Task<Void> emailTask = getEmailAsync();

            new Thread(emailTask).start(); // Run the task in its own thread

            Platform.runLater(cancel_btn.getScene().getWindow()::hide);
        }
    }

    private Task<Void> getEmailAsync() {
        Task<Void> emailTask = new Task<>() {
            @Override
            protected Void call() {
                try {
                    sendAccountDetailEmail();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        emailTask.setOnSucceeded(event -> {
            // UI update after email sent can go here, executed on JavaFX Application Thread
            AlertNotificationUtils.showInformationMessageAlert("Email sent", "Account details emailed successfully");
            System.out.println("Email sent");
        });

        emailTask.setOnFailed(event -> {
            // UI update after email sending failure can go here
            System.out.println("Email failed");
            AlertNotificationUtils.showErrorMessageAlert("Email failed", "Failed to send account details");
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
                || userGender_CB.getValue().isEmpty()
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
        userGender_CB.setValue(null);
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
        String randomPassword = InformationGeneratorUtils.generatePassword(12);
        password_TF.setText(randomPassword);

    }


    @FXML
    private void sendAccountDetailEmail() throws Exception {

        // Check if the username and password fields are empty
        if (userName_TF.getText().isEmpty() || password_TF.getText().isEmpty()) {

            AlertNotificationUtils.showErrorMessageAlert("Empty Fields", "Cannot send details without a username and password");
        } else {
            // Send the email
            GMailUtils.sendEmailTo(userEmail_TF.getText(),"User Account Details",
                    GMailUtils.generateAccountCreationEmailBody(
                            userFirstName_TF.getText(),
                            userName_TF.getText(),
                            password_TF.getText()));
        }
    }


    @FXML
    private void utilityAccountSetup(){
        dob_DP.setValue(LocalDate.now().minusYears(20));
        userWorkPhone_TF.setText("12345678910");
        expiresAt_DP.setValue(LocalDate.now().plusYears(5));
        for (DepartmentModel department : departmentList) {
            if (department.getDeptName().equals("IT")) {
                //setting the default department to IT department for those assets in shared spaces/hotdesk
                userDept_CB.setValue(department);
            }
        }
        for (UserRoleModel role : userRoleList) {
            if (role.getRoleName().equals("Utility")) {
                //setting the default role to IT department for those assets in shared spaces/hotdesk
                accountRole_CB.setValue(role);
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createdOn_DP.setValue(LocalDate.now());
        userEmail_TF.setText(DEFAULT_EMAIL);
        userGender_CB.setItems(FXCollections.observableArrayList("Male","Female", "Utility"));
        accountStatus_CB.setValue("Active");
        DateTimeUtils.dateTimeUpdates(dateTimeHolder);

        //
        userDept_CB.setItems(FXCollections.observableArrayList(departmentList)); // Populate the department choice box
        accountRole_CB.setItems(FXCollections.observableArrayList(userRoleList));// Populate the role choice box


        // Listen for changes in the first name field
        userFirstName_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2 && userLastName_TF.getText().length() >= 2) {
                userName_TF.setText(InformationGeneratorUtils.generateUsername(userFirstName_TF.getText(), userLastName_TF.getText()));
            } else {
                userName_TF.setText("");
            }
        });

        // Listen for changes in the last name field
        userLastName_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >=2 && userFirstName_TF.getText().length() >= 2) {
                userName_TF.setText(InformationGeneratorUtils.generateUsername(userFirstName_TF.getText(), userLastName_TF.getText()));

            } else {
                userName_TF.setText("");
            }
        });

        userGender_CB.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("Utility")) {
                    // Set the default values for utility accounts
                    utilityAccountSetup();
                    }
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
        // Listen for changes in the username field
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
        TextFieldListenerUtils.phoneNumberTextFieldListener(userWorkPhone_TF);

        DateTimeUtils.dobValidator(dob_DP);
        DateTimeUtils.dateValidator(expiresAt_DP);




    }
}
