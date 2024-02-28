package com.example.fyp_application.Controllers.Client;

import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Utils.PasswordHashHandler;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;


import javafx.beans.value.ChangeListener;

import java.net.URL;
import java.util.ResourceBundle;




import java.util.Objects;


public class EditProfilePopUpController implements Initializable {

    @FXML
    private Button cancel_btn;

    @FXML
    private PasswordField confirmationPassword_TF;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private Button saveProfileChanges_btn;

    @FXML
    private TextField email_TF;

    @FXML
    private TextField firstName_TF;

    @FXML
    private ChoiceBox<String> gender_CB;

    @FXML
    private TextField lastName_TF;

    @FXML
    private PasswordField newPassword_TF;

    @FXML
    private Label passwordChecker_lbl;

    @FXML
    private PasswordField password_TF;

    @FXML
    private TextField phone_TF;

    @FXML
    private Circle userProfileHolder;

    @FXML
    private Circle userProfilePhoto;

    private static final UserDAO USER_DAO = new UserDAO();

    private static final AlertNotificationHandler ALERT_HANDLER = new AlertNotificationHandler();


    private int userID;


    @FXML
    private void cancelAction(){
        cancel_btn.getScene().getWindow().hide();


    }



/*    public String hashPassword(String password) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Perform the hashing
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array into a hexadecimal string
            BigInteger no = new BigInteger(1, hashedBytes);
            StringBuilder hexString = new StringBuilder(no.toString(16));

            // Pad with leading zeros
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            throw new RuntimeException(e);
        }
    }*/

    public void loadUserDetails(UserModel currentUser) {

        this.userID = currentUser.getUserID();
        System.out.println(userID);
        firstName_TF.setText(currentUser.getFirstName());
        lastName_TF.setText(currentUser.getLastName());
        email_TF.setText(currentUser.getEmail());
        phone_TF.setText(currentUser.getPhone());


        gender_CB.setValue(currentUser.getGender());
        dateTimeHolder.setText("Last Updated: " + currentUser.getLastUpdated());
        Image currentImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(currentUser.getPhoto())));
        userProfileHolder.setFill(new ImagePattern(currentImage));

    }

    @FXML
    private void saveProfileChanges(){
/*        String password = password_TF.getText();
        String hashedPassword = PasswordHashHandler.hashPassword(password);

        System.out.println(password);
        System.out.println(hashedPassword);

        System.out.println(PasswordHashHandler.verifyPassword(hashedPassword, password));*/


        if(isValidFields()){
            ALERT_HANDLER.showErrorMessageAlert("Empty Fields", "Please fill in all fields");
        }
        else{

            String hashedPassword = PasswordHashHandler.hashPassword(confirmationPassword_TF.getText());

            UserDAO.updateCurrentLoggedUserProfile(this.userID, firstName_TF.getText(), lastName_TF.getText(), email_TF.getText(), phone_TF.getText(),gender_CB.getValue(), hashedPassword);
            ALERT_HANDLER.showInformationMessageAlert("Update Completed", "Supplier information updated successfully");
            //saveProfileChanges_btn.getScene().getWindow().hide();
        }


    }

    @FXML
    private boolean isValidFields(){
        return firstName_TF.getText().isEmpty()
                || lastName_TF.getText().isEmpty()
                || email_TF.getText().isEmpty()
                || phone_TF.getText().isEmpty()
                || gender_CB.getValue().isEmpty();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gender_CB.setItems(FXCollections.observableArrayList("Male", "Female"));

        ChangeListener<String> passwordChangeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Check if the text in both password fields matches
                if (newPassword_TF.getText().equals(confirmationPassword_TF.getText()) || confirmationPassword_TF.getText().equals(newPassword_TF.getText())) {
                    // If they match, indicate success in some way
                    newPassword_TF.setStyle("-fx-border-color: green");
                    confirmationPassword_TF.setStyle("-fx-border-color: green");
                    passwordChecker_lbl.setStyle("-fx-text-fill: green");
                    passwordChecker_lbl.setText("Passwords match");
                } else {
                    // If they do not match, show the label or indicate the mismatch
                    newPassword_TF.setStyle("-fx-border-color: red");
                    confirmationPassword_TF.setStyle("-fx-border-color: red");
                    passwordChecker_lbl.setStyle("-fx-text-fill: red");
                    passwordChecker_lbl.setText("Passwords do not match");
                }
            }
        };

        // Add the listener to both password fields' text properties
        newPassword_TF.textProperty().addListener(passwordChangeListener);
        confirmationPassword_TF.textProperty().addListener(passwordChangeListener);
    }

}
