package com.example.fyp_application.Controllers.Admin.UserManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;

import java.sql.SQLException;
import java.time.LocalDate;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifiedEditUserController implements Initializable{

    @FXML
    private ChoiceBox<String> accStatus_CB;

    @FXML
    private ChoiceBox<UserRoleModel> accountRole_CB;

    @FXML
    private ChoiceBox<DepartmentModel> dept_CB;

    @FXML
    private Button cancel_btn;

    @FXML
    private PasswordField confirmationPassword_TF1;

    @FXML
    private DatePicker createdDate_DP;

    @FXML
    private Label dateTimeHolder;


    @FXML
    private DatePicker dob_DP;

    @FXML
    private DatePicker expiryDate_DP;

    @FXML
    private PasswordField newPassword_TF1;

    @FXML
    private Label passwordChecker_lbl1;

    @FXML
    private Button randomPassword_btn;

    @FXML
    private Button saveProfileChanges_btn;

    @FXML
    private Button updatePassword_btn1;

    @FXML
    private TextField userEmail_TF;

    @FXML
    private TextField userFirstName_TF;

    @FXML
    private ChoiceBox<String> userGender_CB;

    @FXML
    private TextField userLastName_TF;

    @FXML
    private TextField userPhone_TF;

    @FXML
    private Circle userProfileHolder1;

    @FXML
    private Circle userProfilePhoto1;

    @FXML
    private TextField username_TF;

    UserRoleDAO USER_ROLE_DAO = new UserRoleDAO();

    DepartmentDAO DEPARTMENT_DAO = new DepartmentDAO();




    private int userID;




    @FXML
    private void saveProfileChanges() throws SQLException {

        // Show a confirmation alert before saving the changes
        boolean confirmation = AlertNotificationHandler.showConfirmationAlert("Save Profile Changes", "Are you sure you want to save the changes made to this user's profile?");


        // Check if the user has confirmed the changes
        if (isEmptyField()){
            AlertNotificationHandler.showErrorMessageAlert("Missing Information", "Please fill in all required fields.");

        } else {
            // Proceed with saving the changes
            UserDAO.updateUserProfile(userID,
                    accountRole_CB.getValue().getUserRoleID(),
                    dept_CB.getValue().getDeptID(),
                    userFirstName_TF.getText(),
                    userLastName_TF.getText(),
                    userGender_CB.getValue(),
                    userPhone_TF.getText(),
                    userEmail_TF.getText(),
                    accStatus_CB.getValue(),
                    DateTimeHandler.setSQLiteDateFormat(expiryDate_DP.getValue())
                    );

            AlertNotificationHandler.showInformationMessageAlert("Profile Updated", "User profile has been updated successfully.");
            cancel_btn.getScene().getWindow().hide();
        }
    }


    @FXML
    private void cancelUpdate() {
        cancel_btn.getScene().getWindow().hide();
    }


    @FXML
    private void generateRandomPassword() {

        // Generate a random password and display it in the password fields - 12 string length
        String randomPassword = InformationGeneratorHandler.generatePassword(12);

        // Display the generated password in the password fields
        newPassword_TF1.setText(randomPassword);
        confirmationPassword_TF1.setText(randomPassword);
        newPassword_TF1.setEditable(true);
        confirmationPassword_TF1.setEditable(true);

    }


    @FXML
    private void sendPasswordResetEmail() {

        boolean confirmation = AlertNotificationHandler.showConfirmationAlert("Send Password Reset Email", "Are you sure you want to send a password reset email to this user?");

        if (confirmation) {
            // Check if either of the password fields is empty
            if (newPassword_TF1.getText().isEmpty() || confirmationPassword_TF1.getText().isEmpty()) {
                AlertNotificationHandler.showErrorMessageAlert("Missing Information", "Please fill in all required fields.");
                return;
            }

            // Check if the passwords match
            if (newPassword_TF1.getText().equals(confirmationPassword_TF1.getText())) {
                // Passwords match, proceed with sending email
                GMailHandler.sendEmailTo(userEmail_TF.getText(), "Password Reset", GMailHandler.generatePasswordResetEmailBody(userFirstName_TF.getText(), newPassword_TF1.getText()));

                AlertNotificationHandler.showInformationMessageAlert("Email Sent", "Password reset email has been sent to the user.");


                UserDAO.updateUserPassword(userID, PasswordHashHandler.hashPassword(newPassword_TF1.getText()));

                 Platform.runLater(cancel_btn.getScene().getWindow()::hide);

            } else {
                // Passwords do not match, show an error message
                AlertNotificationHandler.showErrorMessageAlert("Password Mismatch", "The new password and the confirmation password do not match. Please try again.");
            }
        }
    }


    private boolean isEmptyField() {

        return (userFirstName_TF.getText().isEmpty() ||
                userLastName_TF.getText().isEmpty() ||
                userEmail_TF.getText().isEmpty() ||
                userPhone_TF.getText().isEmpty() ||
                userGender_CB.getValue().isEmpty() ||
                accStatus_CB.getValue().isEmpty() ||
                accountRole_CB.getValue() == null ||
                dept_CB.getValue() == null) ||
                createdDate_DP.getValue() == null ||
                expiryDate_DP.getValue() == null ||
                dob_DP.getValue() == null;

    }
    private void resetPasswordFields() {
        newPassword_TF1.clear();
        confirmationPassword_TF1.clear();
        passwordChecker_lbl1.setText("");
    }
    public void loadSelectedUserDetails(UserModel user) {

        this.userID = user.getUserID();
        username_TF.setText(user.getUsername());
        userFirstName_TF.setText(user.getFirstName());
        userLastName_TF.setText(user.getLastName());
        userEmail_TF.setText(user.getEmail());
        userPhone_TF.setText(user.getPhone());

        createdDate_DP.setValue(LocalDate.parse(user.getCreatedAt()));
        expiryDate_DP.setValue(LocalDate.parse(user.getExpiresAt()));
        dob_DP.setValue(LocalDate.parse(user.getDOB()));

        Image currentImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(user.getPhoto())));
        userProfileHolder1.setFill(new ImagePattern(currentImage));

        userGender_CB.setValue(user.getGender());
        accStatus_CB.setValue(user.getAccountStatus());

        for (UserRoleModel role : accountRole_CB.getItems()) {
            if (role.getUserRoleID() == user.getUserRoleID()) {
                accountRole_CB.setValue(role);
                break;
            }}

        for (DepartmentModel dept : dept_CB.getItems()) {
            if (dept.getDeptID() == user.getDeptID()) {
                dept_CB.setValue(dept);
                break;
            }
        }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<DepartmentModel> departments = DEPARTMENT_DAO.getAllDepartments();
        dept_CB.getItems().addAll(departments);

        List<UserRoleModel> roles = USER_ROLE_DAO.getAllRoles();
        accountRole_CB.getItems().addAll(roles);

        userGender_CB.setItems(FXCollections.observableArrayList("Male","Female"));
        accStatus_CB.setItems(FXCollections.observableArrayList("Active","Inactive"));



        updatePassword_btn1.setDisable(true);

        ChangeListener<String> passwordChangeListener = (observable, oldValue, newValue) -> {
            // Check if the text in both password fields matches
            if (newPassword_TF1.getText().equals(confirmationPassword_TF1.getText()) || confirmationPassword_TF1.getText().equals(newPassword_TF1.getText())) {
                // If they match, indicate success in some way
                updatePassword_btn1.setDisable(false);
                newPassword_TF1.setStyle("-fx-border-color: green");
                confirmationPassword_TF1.setStyle("-fx-border-color: green");
                passwordChecker_lbl1.setStyle("-fx-text-fill: green");
                passwordChecker_lbl1.setText("Passwords match");

            } else {
                // If they do not match, show the label or indicate the mismatch
                updatePassword_btn1.setDisable(true);
                newPassword_TF1.setStyle("-fx-border-color: red");
                confirmationPassword_TF1.setStyle("-fx-border-color: red");
                passwordChecker_lbl1.setStyle("-fx-text-fill: red");
                passwordChecker_lbl1.setText("Passwords do not match");

            }
        };

        // Add the listener to both password fields' text application.properties
        newPassword_TF1.textProperty().addListener(passwordChangeListener);
        confirmationPassword_TF1.textProperty().addListener(passwordChangeListener);



        expiryDate_DP.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                System.out.println("Invalid date: The date cannot be in the past.");
                AlertNotificationHandler.showInformationMessageAlert("Invalid Date", "The date cannot be in the past.");
                expiryDate_DP.setValue(oldValue);  // Revert to the old value if new value is invalid
                expiryDate_DP.setStyle("-fx-border-color: red");

            } else {
                // Handle valid date selection
                expiryDate_DP.setStyle("-fx-border-color: green");
                System.out.println("Valid date selected: " + newValue);
            }
        });


        accStatus_CB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Inactive")) {
                expiryDate_DP.setDisable(true);
                expiryDate_DP.setValue(null);
            } else {
                expiryDate_DP.setDisable(false);
            }
        });




    }
}
