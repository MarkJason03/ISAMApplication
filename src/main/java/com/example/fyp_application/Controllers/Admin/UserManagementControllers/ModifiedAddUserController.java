package com.example.fyp_application.Controllers.Admin.UserManagementControllers;

import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Utils.InformationGeneratorHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifiedAddUserController implements Initializable {

    @FXML
    private ChoiceBox<?> accountRole_CB;

    @FXML
    private ChoiceBox<?> accountStatus_CB;

    @FXML
    private Button cancel_btn1;

    @FXML
    private PasswordField confirmationPassword_TF1;

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
    private PasswordField newPassword_TF1;

    @FXML
    private Label passwordChecker_lbl1;

    @FXML
    private Button randomPassword_btn;

    @FXML
    private Button updatePassword_btn1;

    @FXML
    private ChoiceBox<?> userDept_CB;

    @FXML
    private TextField userEmail_TF;

    @FXML
    private TextField userFirstName_TF;

    @FXML
    private ChoiceBox<?> userGender_TF;

    @FXML
    private TextField userLastName_TF;

    @FXML
    private TextField userName_TF;

    @FXML
    private TextField userWorkPhone__TF;

    AlertNotificationHandler ALERT_HANDLER = new AlertNotificationHandler();


    @FXML
    private void cancelCreateUserAction(){
        // Close the window
        cancel_btn1.getScene().getWindow().hide();
    }


    @FXML
    private boolean isValidDateOfBirth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate eighteenYearsAgo = currentDate.minusYears(18);

        if (dob_DP.getValue() != null && dob_DP.getValue().isAfter(eighteenYearsAgo)) {
            System.out.println("Invalid date: The date cannot be in the future.");
            ALERT_HANDLER.showInformationMessageAlert("Invalid Date", "The date cannot be in the past.");
            dob_DP.setStyle("-fx-border-color: red");
            return false;
        } else {
            // Handle valid date selection
            System.out.println("Valid date selected: " + dob_DP.getValue());
            dob_DP.setStyle("-fx-border-color: green");
            return true;
        }
    }

    private boolean isValidExpiryDate(){
        if (expiresAt_DP.getValue() != null && expiresAt_DP.getValue().isBefore(LocalDate.now())) {
            System.out.println("Invalid date: The date cannot be in the past.");
            ALERT_HANDLER.showInformationMessageAlert("Invalid Date", "The date cannot be in the past.");
            expiresAt_DP.setStyle("-fx-border-color: red");
            return false;
        } else {
            // Handle valid date selection
            expiresAt_DP.setStyle("-fx-border-color: green");
            System.out.println("Valid date selected: " + expiresAt_DP.getValue());
            return true;
        }
    }


    @FXML
    private void addUser(){
        if (!isValidFields() && isValidUsername() && isValidDateOfBirth() && isValidExpiryDate()) {
            System.out.println("Valid information");
        }else {
            System.out.println("Invalid information");
        }
    }

    @FXML
    private boolean isValidFields(){
        return  userName_TF.getText().isEmpty()
                || userFirstName_TF.getText().isEmpty()
                || userLastName_TF.getText().isEmpty()
                || userEmail_TF.getText().isEmpty()
                || userWorkPhone__TF.getText().isEmpty()
/*                || userGender_TF.getValue().isEmpty()
                || userDept_CB.getValue().isEmpty()
                || accountRole_CB.getValue().isEmpty()
                || accountStatus_CB.getValue().isEmpty()*/
                || newPassword_TF1.getText().isEmpty()
                || confirmationPassword_TF1.getText().isEmpty();
    }
    private boolean isValidUsername(){
        if (UserDAO.isUsernameTaken(userName_TF.getText())) {
            userName_TF.setStyle("-fx-border-color: red");
            return false;
        } else {
            userName_TF.setStyle("-fx-border-color: green");
            return true;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createdOn_DP.setValue(LocalDate.now());



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


        userName_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (UserDAO.isUsernameTaken(newValue)) {

                userName_TF.setStyle("-fx-border-color: red");
            } else {
                userName_TF.setStyle("-fx-border-color: green");
            }
        });






        expiresAt_DP.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                System.out.println("Invalid date: The date cannot be in the past.");
                ALERT_HANDLER.showInformationMessageAlert("Invalid Date", "The date cannot be in the past.");
                expiresAt_DP.setValue(oldValue);  // Revert to the old value if new value is invalid
                expiresAt_DP.setStyle("-fx-border-color: red");

            } else {
                // Handle valid date selection
                expiresAt_DP.setStyle("-fx-border-color: green");
                System.out.println("Valid date selected: " + newValue);
            }
        });


    }
}
