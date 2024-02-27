package com.example.fyp_application.Controllers.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

public class ProfilePopUpController {

    @FXML
    private Label dateTimeHolder;

    @FXML
    private Button editProfile_btn;

    @FXML
    private TextField email_TF;

    @FXML
    private TextField firstName_TF;

    @FXML
    private ChoiceBox<?> gender_CB;

    @FXML
    private TextField gender_TF;

    @FXML
    private TextField lastName_TF;

    @FXML
    private PasswordField password_TF;

    @FXML
    private TextField phone_TF;

    @FXML
    private Circle userProfileHolder;

    @FXML
    private Circle userProfilePhoto;

    @FXML
    private Button cancel_btn;



    @FXML
    private void cancelAction(){
        cancel_btn.getScene().getWindow().hide();
    }

}
