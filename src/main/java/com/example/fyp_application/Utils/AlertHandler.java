package com.example.fyp_application.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertHandler {

    private Alert alert;


    //Showing an error message to the user
    public boolean showErrorMessageAlert(String title, String Message){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(Message);

        Optional<ButtonType> option = alert.showAndWait();

        return option.get().equals(ButtonType.OK);
    }

    //Showing Alert with Confirmation
    public boolean showInformationMessageAlert(String title, String Message) {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(Message);
        Optional<ButtonType> option = alert.showAndWait();
        return option.get().equals(ButtonType.OK);
    }



    //Showing Alert with Confirmation
    public boolean showConfirmationAlert(String title, String Message){
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(Message);

        Optional<ButtonType> option = alert.showAndWait();

        return option.get().equals(ButtonType.OK);
    }

}
